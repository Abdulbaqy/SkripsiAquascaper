package com.example.multiuserrealtimefragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;

import com.example.multiuserrealtimefragment.view.Login;

public class LoadingAlert {

    private Activity activity;
    public AlertDialog dialog;
    public LoadingAlert(Activity myActivity){
        activity = myActivity;
    }

    public void startAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_layout, null);
        builder.setView(dialogView);

        builder.setCancelable(true);

        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();

    }

    public void closeAlertDialog(){
        dialog.dismiss();
    }

}
