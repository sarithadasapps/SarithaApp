package com.iprismtech.foodninjadeliveryapp.LocationUpdateService;

import android.Manifest;
import android.app.IntentService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.iprismtech.foodninjadeliveryapp.Others.HelperObj;
import com.iprismtech.foodninjadeliveryapp.Others.UserSession;
import com.iprismtech.foodninjadeliveryapp.Others.Values;
import com.iprismtech.foodninjadeliveryapp.Others.WebService;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class LocationUpdateService extends IntentService implements LocationListener {


    private static final String TAG = "LocationUpdateService";
    public static final String BROADCAST_ACTION = "com.iprismtech.foodninjadeliveryapp.MyBroadcastReceiver";
    private final Handler handler = new Handler();
    Intent intent;
    LocationManager mLocationManager;
    Double lat, lng;
    boolean isGPSEnable = false;
    boolean isNetworkEnable = false;
    double latitude, longitude;
    LocationManager locationManager;
    Location location;
    private Handler mHandler = new Handler();
    private Timer mTimer = null;
    long notify_interval = 3000;

    public LocationUpdateService() {
        super("LocationUpdateService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
    }


    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        mTimer = new Timer();
        mTimer.schedule(new TimerTaskToGetLocation(), 5, notify_interval);
        return START_STICKY;
    }

    private void fn_getlocation() {
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnable && !isNetworkEnable) {


        } else {

            if (isNetworkEnable) {
                location = null;
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, this);
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {

                        Log.e("latitude", location.getLatitude() + "");
                        Log.e("longitude", location.getLongitude() + "");

                        latitude = location.getLatitude();
                        longitude = location.getLongitude();

                        fn_update(location);
                    }
                }

            }


            if (isGPSEnable) {
                location = null;
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
                if (locationManager!=null){
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location!=null){
                        Log.e("latitude",location.getLatitude()+"");
                        Log.e("longitude",location.getLongitude()+"");
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        fn_update(location);
                    }
                }
            }


        }

    }

    private class TimerTaskToGetLocation extends TimerTask {
        @Override
        public void run() {

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    fn_getlocation();
                }
            });

        }
    }

    private void fn_update(Location location){
        Values.latitude=location.getLatitude();
        Values.longitude=location.getLongitude();
        Handler mHandler=new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                broadcastUpdateInfo();
            }
        },3000);
      //  HelperObj.getInstance().cusToast(getApplicationContext(),  Values.latitude + " From service " + Values.longitude);
       /* handler.removeCallbacks(sendUpdatesToUI);
        handler.postDelayed(sendUpdatesToUI, 3000); // 1 second*/

    }



    private Runnable sendUpdatesToUI = new Runnable() {
        public void run() {
            broadcastUpdateInfo();
            handler.postDelayed(this, 3000); // broadcast in every 10 seconds
        }
    };
    //call your API in this method
    private void broadcastUpdateInfo() {
        Log.d(TAG, "entered DisplayLoggingInfo");
        Map<String, String> params = new HashMap<>();
        params.put("DBID", "" + UserSession.getInstance(getApplicationContext()).getUserDetails().get("KEY_UserID"));
        params.put("lat", "" + Values.latitude);
        params.put("lng", "" + Values.longitude);
        WSPostDataJsonRequestService(getApplicationContext(), WebService.ServerUrl+WebService.set_deliveryboy_location, params);

    }



    //This method is use to send the request to user
    public void WSPostDataJsonRequestService(Context context, final String methodName, final Map<String, String> params) {
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, methodName, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                      //HelperObj.getInstance().cusToast(getApplicationContext(),"Location submitted"+response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        //showVolleyError(context, fragmentManager, error);
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> paramsHeader = new HashMap<>();
//paramsHeader.put("Content-Type","application/x-www-form-urlencoded");
                paramsHeader.put("Content-Type", "application/json");
                paramsHeader.put("Authorization", "Basic Zm9vZGJhc2tldDpmb29kYmFza2V0QDIwMTc=");
                return paramsHeader;
            }
        };
        setJsonRequestQueue(context, jsonRequest);
    }

    /**
     * This method is use to send the reqeust on server to get the response from there.
     *
     * @param context
     * @param request
     */
    private void setJsonRequestQueue(Context context, JsonObjectRequest request) {
        request.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

    @Override
    public void onLocationChanged(Location location) {
       /* lat=location.getLatitude();
        lng=location.getLongitude();
        Values.latitude=lat;
        Values.longitude=lng;
        HelperObj.getInstance().cusToast(getApplicationContext(), lat + " From service " + lng);
        handler.removeCallbacks(sendUpdatesToUI);
        handler.postDelayed(sendUpdatesToUI, 3000); // 1 second*/
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onDestroy() {
        try {
           // handler.removeCallbacks(sendUpdatesToUI);
            intent = new Intent(BROADCAST_ACTION);
            sendBroadcast(intent);
            super.onDestroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
