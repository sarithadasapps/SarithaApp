package com.iprismtech.healthyhome.others;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.media.ExifInterface;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.iprismtech.healthyhome.Dialogues.DialogLoader;
import com.iprismtech.healthyhome.R;
import com.iprismtech.healthyhome.network.constants.NetworkConstants;
import com.iprismtech.healthyhome.network.listener.UpdateWSResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class HelperObj {
    private Toast toast;
    public static String DatePickerValue = "", TimePickerValue = "";
    public static Boolean isAsyncTaskComplete = false;
    private DialogLoader dialog = null;
    private Integer requestTime = 10000;

    private HelperObj() {
    }

    private static HelperObj objInstance;

    public static HelperObj getInstance() {
        if (objInstance == null) {
            objInstance = new HelperObj();
        }
        return objInstance;
    }
    //


    public void TestHandler() {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    //
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 1000);
    }

    public void WSPostData(final Context context, final FragmentManager fragmentManager, final UpdateWSResponse updateListener, final String methodName, final Map<String, String> params, String requestType, String loaderMessage) {
        if (requestType == null) {
            requestType = "";
        }
        Loader(context, fragmentManager, true, loaderMessage);
        final String finalRequestType = requestType;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://mobileappdevelopmentsolutions.com/carwashkaro/webservices/index/getTimings",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            updateListener.updateWSResponse(response, finalRequestType);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Loader(context, fragmentManager, false, null);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (finalRequestType.equals("FragRegisterBloodGroups")) {
                            //  ActivitySplash.whichNetworkError = "FragRegister";
                        }
                        showVolleyError(context, fragmentManager, error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };
        setRequestQueue(context, stringRequest);
    }

    public void setRequestQueue(Context context, StringRequest stringRequest) {
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(requestTime,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public void Loader(Context context, FragmentManager fragmentManager, Boolean isHidden, String message) {
        try {
            if (message == null || message.isEmpty()) {
                message = "Processing...";
            }
            if (isHidden) {
                isAsyncTaskComplete = false;
                dialog = new DialogLoader();
                dialog.setDialogTitle(context, message);
                dialog.setCancelable(false);
                if (dialog != null) {
                    try {
                        dialog.show(fragmentManager, "DialogLoader");
                    } catch (Exception e) {
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.add(dialog, "DialogLoader");
                        transaction.commitAllowingStateLoss();
                    }
                    //
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                isAsyncTaskComplete = true;
                                if (dialog != null) {
                                    dialog.dismiss();
                                    dialog = null;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, requestTime);
                }
            } else {
                isAsyncTaskComplete = true;
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showVolleyError(Context context, FragmentManager fragmentManager, VolleyError error) {
        /*String message = "";
        if (error instanceof NetworkError) {
            message = "Cannot connect to Internet...Please check your connection!";
        } else if (error instanceof ServerError) {
            message = "The server could not be found. Please try again after some time!!";
        } else if (error instanceof AuthFailureError) {
            message = "Cannot connect to Internet...Please check your connection!";
        } else if (error instanceof ParseError) {
            message = "Parsing error! Please try again after some time!!";
        } else if (error instanceof NoConnectionError) {
            message = "Cannot connect to Internet...Please check your connection!";
        } else if (error instanceof TimeoutError) {
            message = "Connection TimeOut! Please check your internet connection.";
        }*/
        /*if(error.networkResponse == null){
            showNetworkError(context,fragmentManager);
        }*/
        if (error instanceof NetworkError) {
            // showNetworkError(context,fragmentManager);
        } else if (error instanceof TimeoutError) {
            cusToastLong(context, "Time out error");
        } else {
            try {
                String errorMessage = error.getMessage();
                if (errorMessage == null || errorMessage.isEmpty()) {
                    errorMessage = "Unknown error occurred";
                }
                cusToast(context, errorMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Loader(context, fragmentManager, false, null);
    }

    /*public void showNetworkError(Context context, FragmentManager fragmentManager){
        DialogNetworkError dialog = new DialogNetworkError();
        dialog.setDialogTitle(context);
        dialog.setCancelable(false);
        dialog.show(fragmentManager, "DialogNetworkError");
    }*/
    public Boolean checkResponse(Context context, String response) {
        try {
            JSONObject jsonResponse = new JSONObject(response);
            JSONObject jsonObject = jsonResponse.optJSONObject("response");
            String success = jsonObject.optString("message");
            if (success.equalsIgnoreCase("success")) {
                return true;
            } else {
                String failureMessage = jsonObject.optString("errormessage");
                if (!failureMessage.isEmpty()) {
                    cusToast(context, failureMessage);
                } else {
                    cusToast(context, "Unknown error occurred");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            cusToast(context, e.getMessage());
        }
        return false;
    }

    public void setGlideDrawableImage(Context context, final ImageView imageView, Integer resourceId, final Boolean isRound) {
        try {
            Glide.with(context)
                    .load(resourceId)
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setGlideImage(Context context, final ImageView imageView, String Url, final Boolean isRound) {
        try {
            Glide.with(context)
                    .load(NetworkConstants.URL.IMAGEPATH + Url)
                    .asBitmap()
                    .centerCrop()
                    .override(200, 200)
                    .placeholder(R.drawable.image_thumbnail)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                            if (resource != null) {
                                if (isRound) {
                                    setRoundImage(resource, imageView);
                                } else {
                                    imageView.setImageBitmap(resource);
                                }
                            }
                        }

                        @Override
                        public void onLoadFailed(Exception e, Drawable errorDrawable) {
                            imageView.setImageResource(R.drawable.image_thumbnail);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*   public void setGlideDrawableImage(Context context, final ImageView imageView,Integer resourceId, final Boolean isRound){
           try {
               Glide.with(context)
                       .load(resourceId)
                       .into(imageView);
           } catch (Exception e) {
               e.printStackTrace();
           }
       }*/
    public String GetJson(ArrayList<String> ValuesList) {
        //String json = "{\"json\":[{";
        String json = "{";
        for (int i = 0; i < ValuesList.size(); i++) {
            String[] Split = ValuesList.get(i).split(" ☢");
            if (Split.length == 1) {
                Split = new String[]{Split[0], " "};
            }
            json += '"' + Split[0] + '"' + ':' + '"' + jsonEscapeCharacters(Split[1]) + '"';
            if (i + 1 != ValuesList.size()) {
                json += ",";
            }
        }
        //json += "}]}";
        json += "}";
        return json;
    }

    public String getLocationName(Context context, double latitude, double longitude) {
        String cityName = "Not Found";
        Geocoder gcd = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = gcd.getFromLocation(latitude, longitude, 10);
            for (Address adrs : addresses) {
                if (adrs != null) {
                    String area = adrs.getFeatureName();
                    String city = adrs.getSubLocality();
                    if (city != null && !city.equals("")) {
                        cityName = city;
                        return area + ", " + cityName;
                    } else {
                    }
                    // // you should also try with addresses.get(0).toSring();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cityName;
    }

    public String getLocationFullAddress(Context context, double latitude, double longitude) {
        String cityName = "Not Found";
        Geocoder gcd = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = gcd.getFromLocation(latitude, longitude, 10);
            for (Address adrs : addresses) {
                if (adrs != null) {
                    String area = adrs.getFeatureName();
                    String city = adrs.getSubLocality();
                    String locality = adrs.getLocality();
                    String state = adrs.getAdminArea();
                    if (city != null && !city.equals("")) {
                        cityName = city;
                        return area + ", \n" + cityName + ", \n" + locality + ", \n" + state;
                    } else {
                    }
                    // // you should also try with addresses.get(0).toSring();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cityName;
    }

    public String jsonEscapeCharacters(String value) {
        try {
            value = value.replace("\n", "\\n")
                    .replace("'", "\"")
                    .replace("\"", "\\\"");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public void HideKeyboard(View v) {
        try {
            if (v != null) {
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ShowKeyboard(View v) {
        try {
            /*if(v != null){
                InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    //imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    imm.showSoftInput(v,0);
                }
            }*/
            InputMethodManager inputMethodManager = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Boolean IsEvenNumber(Integer value) {
        return (value % 2) == 0;
    }

    public Boolean IsNull(String value) {
        if (value == null) {
            return true;
        } else
            return value.trim().equals("") || value.equals("null") || value.equals("NULL") || value.equals("Null");
    }

    public String IsNullReturnValue(String value, String returnValue) {
        if (value == null) {
            return returnValue;
        } else if (value.trim().equals("") || value.equals("null") || value.equals("NULL") || value.equals("Null")) {
            return returnValue;
        } else {
            return value;
        }
    }

    public Boolean IsEmpty(String value) {
        if (value == null) {
            return true;
        } else
            return value.trim().equals("") || value.equals("null") || value.equals("NULL") || value.equals("Null");
    }

    public boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public JSONArray GetJSONArray(Cursor cursor) {
        JSONArray resultSet = new JSONArray();
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int totalColumn = cursor.getColumnCount();
                JSONObject rowObject = new JSONObject();
                for (int i = 0; i < totalColumn; i++) {
                    if (cursor.getColumnName(i) != null) {
                        try {
                            if (cursor.getString(i) != null) {
                                rowObject.put(cursor.getColumnName(i), cursor.getString(i));
                            } else {
                                rowObject.put(cursor.getColumnName(i), "");
                            }
                        } catch (Exception e) {
                            Log.d("TAG_NAME", e.getMessage());
                        }
                    }
                }
                resultSet.put(rowObject);
                cursor.moveToNext();
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public int ArrayListStringIndex(ArrayList<String> arrayList, String value) {
        if (arrayList != null) {
            return arrayList.indexOf(value);
        } else {
            return -1;
        }
    }

    public int ArrayListIntIndex(ArrayList<Integer> arrayList, Integer value) {
        if (arrayList != null) {
            return arrayList.indexOf(value);
        } else {
            return -1;
        }
    }

    public String getSum(ArrayList<String> arrayList, String value) {
        String result = "";
        Integer intValue = 0;
        Double doubleValue = 0.00;
        if (value.equals("int")) {
            for (int i = 0; i < arrayList.size(); i++) {
                intValue += Integer.parseInt(arrayList.get(i));
            }
            result = String.valueOf(intValue);
        } else if (value.equals("double")) {
            for (int i = 0; i < arrayList.size(); i++) {
                doubleValue += Double.parseDouble(arrayList.get(i));
            }
            result = String.valueOf(new DecimalFormat("##.##").format(doubleValue));
            Double valueDouble = Double.parseDouble(result);
            int splitter = valueDouble.toString().length();
            DecimalFormat format;
            if (splitter > 7) {
                format = new DecimalFormat("#0,00,000.00");
            } else if (splitter > 5) {
                format = new DecimalFormat("#0,000.00");
            } else {
                format = new DecimalFormat("#0.00");
            }
            result = format.format(valueDouble);
        }
        return result;
    }

    public String formatAmount(Integer price) {
        try {
            String result = String.valueOf(new DecimalFormat("##.##").format(price));
            Double valueDouble = Double.parseDouble(result);
            int splitter = valueDouble.toString().length();
            DecimalFormat format;
            if (splitter > 7) {
                format = new DecimalFormat("#0,00,000.00");
            } else if (splitter > 5) {
                format = new DecimalFormat("#0,000.00");
            } else {
                format = new DecimalFormat("#0.00");
            }
            return format.format(valueDouble);
        } catch (Exception e) {
            return "";
        }
    }

    public double RoundDouble(double value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        //String result = String.valueOf(new DecimalFormat("##.##").format(bd.doubleValue()));
        return bd.doubleValue();
    }

    public int ArrayIndex(String searchString, String[] domain) {
        for (int i = 0; i < domain.length; i++) {
            if (searchString.equals(domain[i])) {
                return i;
            }
        }
        return -1;
    }

    public Boolean isInt(String input) {
        Boolean id;
        try {
            Integer ID = Integer.parseInt(input.trim());
            id = ID != 0;
        } catch (Exception e) {
            id = false;
        }
        return id;
    }

    public void cusToast(final Context context, final String message) {
        try {
            Handler UIHandler = new Handler(Looper.getMainLooper());
            UIHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (toast != null) {
                        toast.cancel();
                    }
                    toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cusToastLong(final Context context, final String message) {
        try {
            Handler UIHandler = new Handler(Looper.getMainLooper());
            UIHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (toast != null) {
                        toast.cancel();
                    }
                    toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
                    toast.show();
                }
            }, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //
    public File CreateThumbImage(File Source, File Destination, int quality) {
        try {
            Bitmap scaledBitmap = null;
            BitmapFactory.Options options = new BitmapFactory.Options();
//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
            options.inJustDecodeBounds = true;
            Bitmap bmp = BitmapFactory.decodeFile(Source.getPath(), options);
            int actualHeight = options.outHeight;
            int actualWidth = options.outWidth;
//      max Height and width values of the compressed image is taken as 816x612
            float maxHeight = 816.0f;
            float maxWidth = 612.0f;
            float imgRatio = actualWidth / actualHeight;
            float maxRatio = maxWidth / maxHeight;
//      width and height values are set maintaining the aspect ratio of the image
            if (actualHeight > maxHeight || actualWidth > maxWidth) {
                if (imgRatio < maxRatio) {
                    imgRatio = maxHeight / actualHeight;
                    actualWidth = (int) (imgRatio * actualWidth);
                    actualHeight = (int) maxHeight;
                } else if (imgRatio > maxRatio) {
                    imgRatio = maxWidth / actualWidth;
                    actualHeight = (int) (imgRatio * actualHeight);
                    actualWidth = (int) maxWidth;
                } else {
                    actualHeight = (int) maxHeight;
                    actualWidth = (int) maxWidth;
                }
            }
//      setting inSampleSize value allows to load a scaled down version of the original image
            options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);
//      inJustDecodeBounds set to false to load the actual bitmap
            options.inJustDecodeBounds = false;
//      this options allow android to claim the bitmap memory if it runs low on memory
            options.inPurgeable = true;
            options.inInputShareable = true;
            options.inTempStorage = new byte[16 * 1024];
            try {
                //          load the bitmap from its path
                bmp = BitmapFactory.decodeFile(Source.getPath(), options);
            } catch (OutOfMemoryError exception) {
                exception.printStackTrace();
            }
            try {
                scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
            } catch (OutOfMemoryError exception) {
                exception.printStackTrace();
            }
            float ratioX = actualWidth / (float) options.outWidth;
            float ratioY = actualHeight / (float) options.outHeight;
            float middleX = actualWidth / 2.0f;
            float middleY = actualHeight / 2.0f;
            Matrix scaleMatrix = new Matrix();
            scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);
            Canvas canvas = new Canvas(scaledBitmap);
            canvas.setMatrix(scaleMatrix);
            canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));
//      check the rotation of the image and display it properly
            ExifInterface exif;
            try {
                exif = new ExifInterface(Source.getPath());
                int orientation = exif.getAttributeInt(
                        ExifInterface.TAG_ORIENTATION, 0);
                Log.d("EXIF", "Exif: " + orientation);
                Matrix matrix = new Matrix();
                if (orientation == 6) {
                    matrix.postRotate(90);
                    Log.d("EXIF", "Exif: " + orientation);
                } else if (orientation == 3) {
                    matrix.postRotate(180);
                    Log.d("EXIF", "Exif: " + orientation);
                } else if (orientation == 8) {
                    matrix.postRotate(270);
                    Log.d("EXIF", "Exif: " + orientation);
                }
                if (scaledBitmap != null) {
                    scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(),
                            scaledBitmap.getHeight(), matrix, true);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (scaledBitmap != null) {
                try {
                    FileOutputStream outStream = new FileOutputStream(Destination);
                    scaledBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outStream);
                    outStream.flush();
                    outStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Destination;
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }
        return inSampleSize;
    }

    public void setRoundImage(Bitmap bitmap, ImageView imageView) {
        try {
            bitmap = getCircularBitmap(bitmap);
            bitmap = addBorderToCircularBitmap(bitmap, 3, Color.WHITE);
            bitmap = addShadowToCircularBitmap(bitmap, 2, Color.GRAY);
            imageView.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bitmap getCircularBitmap(Bitmap srcBitmap) {
        int squareBitmapWidth = Math.min(srcBitmap.getWidth(), srcBitmap.getHeight());
        Bitmap dstBitmap = Bitmap.createBitmap(
                squareBitmapWidth, // Width
                squareBitmapWidth, // Height
                Bitmap.Config.ARGB_8888 // Config
        );
        // Initialize a new Canvas to draw circular bitmap
        Canvas canvas = new Canvas(dstBitmap);
        // Initialize a new Paint instance
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        // Initialize a new Rect instance
        Rect rect = new Rect(0, 0, squareBitmapWidth, squareBitmapWidth);
        // Initialize a new RectF instance
        RectF rectF = new RectF(rect);
        // Draw an oval shape on Canvas
        canvas.drawOval(rectF, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        // Calculate the left and top of copied bitmap
        float left = (squareBitmapWidth - srcBitmap.getWidth()) / 2;
        float top = (squareBitmapWidth - srcBitmap.getHeight()) / 2;
        // Make a rounded image by copying at the exact center position of source image
        canvas.drawBitmap(srcBitmap, left, top, paint);
        // Free the native object associated with this bitmap.
        //srcBitmap.recycle();
        // Return the circular bitmap
        return dstBitmap;
    }

    public Bitmap addBorderToCircularBitmap(Bitmap srcBitmap, int borderWidth, int borderColor) {
        // Calculate the circular bitmap width with border
        int dstBitmapWidth = srcBitmap.getWidth() + borderWidth * 2;
        // Initialize a new Bitmap to make it bordered circular bitmap
        Bitmap dstBitmap = Bitmap.createBitmap(dstBitmapWidth, dstBitmapWidth, Bitmap.Config.ARGB_8888);
        // Initialize a new Canvas instance
        Canvas canvas = new Canvas(dstBitmap);
        // Draw source bitmap to canvas
        canvas.drawBitmap(srcBitmap, borderWidth, borderWidth, null);
        // Initialize a new Paint instance to draw border
        Paint paint = new Paint();
        paint.setColor(borderColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(borderWidth);
        paint.setAntiAlias(true);
        // Draw the circular border around circular bitmap
        canvas.drawCircle(
                canvas.getWidth() / 2, // cx
                canvas.getWidth() / 2, // cy
                canvas.getWidth() / 2 - borderWidth / 2, // Radius
                paint // Paint
        );
        // Free the native object associated with this bitmap.
        //srcBitmap.recycle();
        // Return the bordered circular bitmap
        return dstBitmap;
    }

    public Bitmap addShadowToCircularBitmap(Bitmap srcBitmap, int shadowWidth, int shadowColor) {
        // Calculate the circular bitmap width with shadow
        int dstBitmapWidth = srcBitmap.getWidth() + shadowWidth * 2;
        Bitmap dstBitmap = Bitmap.createBitmap(dstBitmapWidth, dstBitmapWidth, Bitmap.Config.ARGB_8888);
        // Initialize a new Canvas instance
        Canvas canvas = new Canvas(dstBitmap);
        canvas.drawBitmap(srcBitmap, shadowWidth, shadowWidth, null);
        // Paint to draw circular bitmap shadow
        Paint paint = new Paint();
        paint.setColor(shadowColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(shadowWidth);
        paint.setAntiAlias(true);
        // Draw the shadow around circular bitmap
        canvas.drawCircle(
                dstBitmapWidth / 2, // cx
                dstBitmapWidth / 2, // cy
                dstBitmapWidth / 2 - shadowWidth / 2, // Radius
                paint // Paint
        );
        //srcBitmap.recycle();
        // Return the circular bitmap with shadow
        return dstBitmap;
    }

    public boolean checkPlayServices(Context context) {
      /*  GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS) {
            try {
                if (apiAvailability.isUserResolvableError(resultCode)) {
                    apiAvailability.getErrorDialog((Activity) context, resultCode, UiAppConstant.PLAY_SERVICES_RESOLUTION_REQUEST).show();
                } else {
//HelperObj.cusToast(context,"This device is not support play services");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }*/
        return true;
    }
}
