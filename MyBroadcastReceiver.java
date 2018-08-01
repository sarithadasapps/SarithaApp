package com.iprismtech.foodninjadeliveryapp.LocationUpdateService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.iprismtech.foodninjadeliveryapp.Others.HelperObj;

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("MyBroadcastReceiver : ", "Service Stops! Oooooooooooooppppssssss!!!!");
        context.startService(new Intent(context, LocationUpdateService.class));
        HelperObj.getInstance().cusToast(context,"Service restarted!");
    }
}