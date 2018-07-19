package com.iprismtech.healthyhome.httplibrary;
/**
 * Created by mustufak on 01-03-2015.
 */


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.iprismtech.healthyhome.R;


public class DemoProgressDialog extends ProgressDialog {

    private static DemoProgressDialog pDialog;
    private static Context context;

    public DemoProgressDialog(Context context, int theme) {
        super(context);
        this.setIndeterminate(true);
        this.setCancelable(false);
        this.setProgressStyle(theme);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setProgressStyle(R.style.MyProgressDialogStyle);
    }

    public void setMessageAndShow(String message) {
        if (!TextUtils.isEmpty(message)) {
            try {
                this.setMessage(message);
                this.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void close() {
        try {
            if (this.isShowing()) {
                this.cancel();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DemoProgressDialog getDialogInstance(Context context1, int theme) {
        if (context == null) {
            context = context1;
        } else {
            if (context != context1) {
                pDialog = null;
                context = context1;
            }
        }
        if (pDialog == null) {
            pDialog = new DemoProgressDialog(context, theme);
        }
        return pDialog;
    }
}