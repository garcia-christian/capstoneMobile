package com.example.heremiStartup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Signin2 extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    LinearLayout skip;
    LoadingDia loading;
    modelCustomer modelCustomer;
    Spinner spinner;
    Integer userId = 0;
    EditText fname, lname, age;
    String selectedSex;
    Button cont;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin2);

        loading = new LoadingDia(Signin2.this);
        spinner = findViewById(R.id.spinner);
        fname = findViewById(R.id.editTextTextPersonName);
        lname = findViewById(R.id.editTextTextPersonName3);
        age = findViewById(R.id.editTextNumber2);
        preferences = getSharedPreferences("User",MODE_PRIVATE);
        editor = preferences.edit();
        getUser();
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Sex");
        arrayList.add("Male");
        arrayList.add("Female");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(parent.getItemAtPosition(position).toString().equals("Male")){
                    selectedSex = "M";
                }else if(parent.getItemAtPosition(position).toString().equals("Female")){
                    selectedSex = "F";
                }else{
                    selectedSex = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                parent.setSelection(0);
            }
        });






        cont = findViewById(R.id.submit_med2);
        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fname.getText().toString().isEmpty()){
                    Toast.makeText(Signin2.this, "Enter Firstname", Toast.LENGTH_SHORT).show();
                }else if(lname.getText().toString().isEmpty()){
                    Toast.makeText(Signin2.this, "Enter Lastname", Toast.LENGTH_SHORT).show();
                }else if(age.getText().toString().isEmpty()){
                    Toast.makeText(Signin2.this, "Enter Age", Toast.LENGTH_SHORT).show();
                }else if(selectedSex.isEmpty()){
                    Toast.makeText(Signin2.this, "Select Sex", Toast.LENGTH_SHORT).show();
                }else{
                    contin();
                }

            }
        });
        skip = findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.loadingDialog();
                startActivity(new Intent(Signin2.this, MainActivity.class));
                loading.dismissDialog();
                finish();
            }
        });
    }

    private void contin() {
        loading.loadingDialog();
        modelCustomer = new modelCustomer();
        modelCustomer.setFirstname(fname.getText().toString());
        modelCustomer.setLastname(lname.getText().toString());
        modelCustomer.setAge(age.getText().toString());
        modelCustomer.setSex(selectedSex);
        Call<ResponseBody> call = apiClient.getDeclaration().userInfo(userId, modelCustomer);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                startActivity(new Intent(Signin2.this, MainActivity.class));
                loading.dismissDialog();
                finish();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
    private void getUser() {

        Call<modelCustomer> call = apiClient.getDeclaration().getUser(preferences.getString("token",""));
        call.enqueue(new Callback<modelCustomer>() {
            @Override
            public void onResponse(Call<modelCustomer> call, Response<modelCustomer> response) {
                if(response.isSuccessful()){
                    userId = response.body().getCustomer_id();

                }
            }

            @Override
            public void onFailure(Call<modelCustomer> call, Throwable t) {
                Toast.makeText(Signin2.this, "Server Failure", Toast.LENGTH_SHORT).show();
            }
        });

    }
}