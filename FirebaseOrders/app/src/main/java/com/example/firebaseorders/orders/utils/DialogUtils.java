package com.example.firebaseorders.orders.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.example.firebaseorders.R;

/**
 * Author : Rakesh.V
 * Date : 08/08/2019
 * Dialog Utils for package
 */
public class DialogUtils {

    private static ProgressDialog mProgressDialog;
    private static DialogUtils INSTANCE;

    public static final DialogUtils getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new DialogUtils();
        }

        return INSTANCE;
    }

    /**
     * Initializing the progressBar
     * @param context
     * @param message
     * @return
     */
    public ProgressDialog showProgressDialog(Context context, String message){
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage(message);
        mProgressDialog.setProgressStyle(R.style.Theme_AppCompat_Light_Dialog_Alert);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
        return mProgressDialog;
    }

    /**
     * * Dismiss the progressBar
     */
    public void dismissProgressDialog(){
        if (mProgressDialog == null)
            return;

        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * Display Toast message to User
     * @param context
     * @param message
     */
    public void displayToastMessage(Context context, String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}
