package com.iprismtech.healthyhome.httplibrary;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.iprismtech.healthyhome.R;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

public class DemoHttpHandler extends AsyncHttpResponseHandler {

    private ProgressDialog pDialog;
    private HttpResponses httpResponses;
    private int identifier;
    private Context context;
    private ListView listView;
    private ViewGroup rootView;
    private View hideView;
    private boolean showDialog = false;

    public DemoHttpHandler(Context context, HttpResponses httpResponses, String message, int identifier) {
        this.httpResponses = httpResponses;
        this.identifier = identifier;
        this.context = context;

        message=context.getString(R.string.pleasewait);

        if (message != null) {
             pDialog = new ProgressDialog(context, R.style.MyAlertDialogTheme);
            pDialog.setMessage(message);
            pDialog.show();
        }

       /* pDialog = DemoProgressDialog.getDialogInstance(context, R.style.MyProgressDialogStyle);
        if (message != null) {
            progressDialog.setMessageAndShow(message);
        }*/
    }

    public void setShowDialog(boolean showDialog) {
        this.showDialog = showDialog;
    }

    public void setListView(ListView listView) {
        this.listView = listView;
    }

    public void setRootView(ViewGroup rootView) {
        this.rootView = rootView;
    }

    public void setHideView(View hideView) {
        this.hideView = hideView;
    }

    public void onSuccess(String responseObject) {
        pDialog.dismiss();
        httpResponses.onSuccess(responseObject, identifier);
    }

    public void onFailure(Throwable e, String errorResponse) {
        pDialog.dismiss();
        e.printStackTrace();
        Toast t = Toast.makeText(context, ""+errorResponse, Toast.LENGTH_LONG);
        t.show();
    }

    public void handleFailureMessage(Throwable e, String errorResponse) {
        pDialog.dismiss();
        Toast t = Toast.makeText(context, "Network Failure, Please check your network Connection", Toast.LENGTH_LONG);
        t.show();
    }

    @Override
    public void onSuccess(int i, Header[] headers, byte[] bytes) {
        String responseObject = new String(bytes);
        pDialog.dismiss();
        httpResponses.onSuccess(responseObject, identifier);
    }

    @Override
    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
        if (bytes == null) {
            pDialog.dismiss();
        } else {
            pDialog.dismiss();
            throwable.printStackTrace();
            Toast t = Toast.makeText(context, "Failed", Toast.LENGTH_LONG);
            t.show();
        }
        httpResponses.onFailure();
    }
}