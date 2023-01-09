package com.example.heremiStartup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Signin extends AppCompatActivity {
    EditText username,email, password;
    TextView login;
    Button signin;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    modelSignin modelSignin;
    LoadingDia loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        loading = new LoadingDia(Signin.this);

        email = findViewById(R.id.email);
        username = findViewById(R.id.username);
        password = findViewById(R.id.editTextTextPassword);
        login =findViewById(R.id.textView16);
        signin = findViewById(R.id.submit_med2);
        preferences = getSharedPreferences("User",MODE_PRIVATE);
        editor = preferences.edit();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Signin.this, Login.class));
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().isEmpty()){
                    Toast.makeText(Signin.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                } else if (username.getText().toString().isEmpty()){
                    Toast.makeText(Signin.this, "Please enter your username", Toast.LENGTH_SHORT).show();
                } else if (password.getText().toString().isEmpty()){
                    Toast.makeText(Signin.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                } else{
                    signinauth();
                }
            }
        });
    }

    private void signinauth() {
        loading.loadingDialog();
        modelSignin = new modelSignin(
                username.getText().toString(),
                password.getText().toString(),
                email.getText().toString());
        Call<modelTokens> call = apiClient.getDeclaration().signin(modelSignin);
        call.enqueue(new Callback<modelTokens>() {
            @Override
            public void onResponse(Call<modelTokens> call, Response<modelTokens> response) {
                if(response.isSuccessful()){
                    Toast.makeText(Signin.this, "Signed in", Toast.LENGTH_SHORT).show();
                   verify(response.body());
                }else if(response.code() == 409){
                    Toast.makeText(Signin.this, "Username is already used", Toast.LENGTH_SHORT).show();
                    loading.dismissDialog();
                }else if(response.code() == 410){
                    Toast.makeText(Signin.this, "Email is already used", Toast.LENGTH_SHORT).show();
                    loading.dismissDialog();
                }else if(response.code() == 403){
                    Toast.makeText(Signin.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                    loading.dismissDialog();
                }
            }

            @Override
            public void onFailure(Call<modelTokens> call, Throwable t) {
                Toast.makeText(Signin.this, "Server Error "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void verify(modelTokens body) {
        Call<ResponseBody> call = apiClient.getDeclaration().verify(body.getAccess());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    editor.putString("token",body.getAccess());
                    editor.commit();
                    startActivity(new Intent(Signin.this, Signin2.class));
                    loading.dismissDialog();
                    finish();
                } else {
                    Toast.makeText(Signin.this, "Invalid Token", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Signin.this, "Server Error "+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}