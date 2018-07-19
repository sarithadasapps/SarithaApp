package com.iprismtech.healthyhome.httplibrary;

/**
 * Created by mustufak on 22-02-2015.
 */


import android.content.Context;

import com.iprismtech.healthyhome.network.utils.Utils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.entity.StringEntity;

import java.util.HashMap;

public class HttpClients {

    private static final String BASE_URL = "http://www.qmobileme.com/healthyhomes/API/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(Context context, String url, RequestParams params, AsyncHttpResponseHandler responseHandler, HashMap<String, String> headers) {

        if (!Utils.checkInternetConnection(context)) {
            Utils.ShowNetworkDialog(context);
        } else {
            if (headers != null) {
                setHeaders(headers);
            }

            if (client == null) {
                client = new AsyncHttpClient();
            }
            client.get(url, params, responseHandler);
        }
    }

    public static void post(Context context, String url, RequestParams params, AsyncHttpResponseHandler responseHandler, HashMap<String, String> headers) {
        try {

            if (!Utils.checkInternetConnection(context)) {
                Utils.ShowNetworkDialog(context);
            } else {
                if(headers != null) {
                    setHeaders(headers);
                }
                if(client == null) {
                    client = new AsyncHttpClient();
                }
                client.post(url, params, responseHandler);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void postJson(Context context,String url, StringEntity entity, RequestParams params, AsyncHttpResponseHandler responseHandler, HashMap<String, String> headers) {

        if (!Utils.checkInternetConnection(context)) {
            Utils.ShowNetworkDialog(context);
        } else {
            if (headers != null) {
                setHeaders(headers);
            }
            if (client == null) {
                client = new AsyncHttpClient();
            }
            client.post(context, url, entity, "application/json", responseHandler);
        }
    }


    private static String getAbsoluteUrl(String relativeUrl) {
        if(relativeUrl.startsWith("http")) {
            return relativeUrl;
        }
        return BASE_URL + relativeUrl;
    }

    private static void generateInstance() {
        client = null;
        client = new AsyncHttpClient();
    }

    private static void setHeaders(HashMap<String, String> headers) {
        generateInstance();
        if(client == null) {
            client = new AsyncHttpClient();
        }
        for(String key : headers.keySet()) {
            client.addHeader(key, headers.get(key));
        }
        //client.setTimeout(Constants.HTTP_TIMEOUT);
        client.setTimeout(60000);
    }


}