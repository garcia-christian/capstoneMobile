package com.example.heremiStartup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    EditText username, password;
    TextView signup;
    Button login;
    modelLogin modelLogin;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.editTextTextPersonName2);
        password = findViewById(R.id.editTextTextPassword);
        login = findViewById(R.id.submit_med2);
        signup = findViewById(R.id.textView16);
        preferences = getSharedPreferences("User",MODE_PRIVATE);
        editor = preferences.edit();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().isEmpty()){
                    Toast.makeText(Login.this, "Input your username", Toast.LENGTH_SHORT).show();
                }else if (password.getText().toString().isEmpty()){
                    Toast.makeText(Login.this, "Input your password", Toast.LENGTH_SHORT).show();

                }else{
                    login();
                }
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Signin.class));
            }
        });
    }

    private void login() {
        modelLogin = new modelLogin(username.getText().toString(), password.getText().toString());

        Call<modelTokens> call = apiClient.getDeclaration().login(modelLogin);

        call.enqueue(new Callback<modelTokens>() {
            @Override
            public void onResponse(Call<modelTokens> call, Response<modelTokens> response) {
                if(response.isSuccessful()){

                    verify(response.body());

                    Toast.makeText(Login.this, "Logged in", Toast.LENGTH_SHORT).show();
                } else if(response.code()==404){
                    Toast.makeText(Login.this, "User not Found", Toast.LENGTH_SHORT).show();
                    username.setText("");
                    password.setText("");
                } else if(response.code()==403){
                    Toast.makeText(Login.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                    password.setText("");
                }
            }

            @Override
            public void onFailure(Call<modelTokens> call, Throwable t) {
                Toast.makeText(Login.this, "Server Error "+t.getMessage(), Toast.LENGTH_SHORT).show();
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
                    startActivity(new Intent(Login.this, MainActivity.class));
                } else {
                    Toast.makeText(Login.this, "Invalid Token", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Login.this, "Server Error "+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

}