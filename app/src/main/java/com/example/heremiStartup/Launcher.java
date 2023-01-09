package com.example.heremiStartup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Launcher extends AppCompatActivity {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        preferences = getSharedPreferences("User",MODE_PRIVATE);
        editor = preferences.edit();

        if(preferences.contains("token")){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                   verify();
                }
            }, 4000);
        }else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Launcher.this, Login.class));
                    finish();
                }
            }, 4000);
        }

    }
    private void verify() {
        Call<ResponseBody> call = apiClient.getDeclaration().verify(preferences.getString("token",""));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    startActivity(new Intent(Launcher.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(Launcher.this, "Invalid Token", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Launcher.this, Login.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                startActivity(new Intent(Launcher.this, Login.class));
                finish();
            }
        });

    }
}