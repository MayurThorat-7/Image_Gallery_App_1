package com.example.imagegalleryapp;

import android.app.ProgressDialog;
import android.content.Context;

public class Utils {

    static ProgressDialog progressDialog;

    public static void showProgress(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getResources().getString(R.string.loading));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public static void dismissProgress() {
        progressDialog.dismiss();
    }
}
