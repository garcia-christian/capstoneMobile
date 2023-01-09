package com.example.heremiStartup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class addMed extends AppCompatActivity {
    ImageView backbtn;
    EditText med_name,med_qty,med_type;
    Button submit_med;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_med);
        med_name = findViewById(R.id.med_name);
        med_qty = findViewById(R.id.med_qty);

        backbtn = findViewById(R.id.back_btn_med);
        submit_med = findViewById(R.id.submit_med);

        submit_med.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            save(createmodelMedicine());
            }
        });








        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public modelMedicine createmodelMedicine(){
        modelMedicine modelMedicine = new modelMedicine();
        modelMedicine.setProd_id(0);
        modelMedicine.setMed_name(med_name.getText().toString());
        modelMedicine.setMed_quantity(Integer.parseInt(med_qty.getText().toString()));
        modelMedicine.setType_id(Integer.parseInt(med_type.getText().toString()));

        return modelMedicine;
    }

    public void save(modelMedicine modelMedicine){
        Call<List<modelMedicineRes>> modelMedicineCall = apiClient.getDeclaration().saveMedicine(modelMedicine);

        modelMedicineCall.enqueue(new Callback<List<modelMedicineRes>>() {
            @Override
            public void onResponse(Call<List<modelMedicineRes>> call, Response<List<modelMedicineRes>> response) {
               if(response.isSuccessful()){

                   Toast.makeText(addMed.this,"Saved Successfully",Toast.LENGTH_LONG).show();
                   onBackPressed();
               }else{
                   Toast.makeText(addMed.this,"Saving Failed ",Toast.LENGTH_LONG).show();
               }


            }

            @Override
            public void onFailure(Call<List<modelMedicineRes>>call, Throwable t) {
                Toast.makeText(addMed.this,"Saving Failed "+t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

}