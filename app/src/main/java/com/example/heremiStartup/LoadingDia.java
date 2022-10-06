package com.example.heremiStartup;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;

public class LoadingDia {

        Activity activity;
        AlertDialog alertDialog;

    public LoadingDia(Activity activity) {
        this.activity = activity;
    }

    void loadingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.layout_loading,null));
        builder.setCancelable(false);


        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }

    void dismissDialog(){
        alertDialog.dismiss();
    }

}
