package com.iprismtech.healthyhome.others;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.iprismtech.healthyhome.activity.ActivityLogin;
import com.iprismtech.healthyhome.app.constants.UiAppConstant;

import java.util.HashMap;

/**
 * Created by iprismTech on 4/20/2017.
 */

public class UserSession {
    // Shared Preferences reference
    SharedPreferences pref;

    // Editor reference for Shared preferences
    static SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared preferences mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    public static final String PREFER_NAME = "Reg";

    // All Shared Preferences Keys
    public static final String IS_USER_LOGIN = "IsUserLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_USERID = "userid";
    // User name (make variable public to access from outside)
    public static final String KEY_USERNAME = "username";

    public static final String KEY_EMAIL = "email";
    // password (make variable public to access from outside)
    public static final String KEY_MOBILENO = "mobileno";

    public static final String KEY_PASSWORD = "password";

    public static final String KEY_AddressFlatNo="KEY_AddressFlatNo";
    public static final String KEY_AddressLocalityArea="KEY_AddressLocalityArea";
    public static final String KEY_AddressCity="KEY_AddressCity";
    public static final String KEY_AddressState="KEY_AddressState";
    public static final String KEY_AddressPostalCode="KEY_AddressPostalCode";
    public static final String KEY_Country="KEY_Country";
    public static final String KEY_SelectedLanguage="language";
    public static final String KEY_IsLanguageSelected="islanguageselected";
    public static final String KEY_TOKEN="token";
    public static final String KEY_NotificationRegID="regid";
    public static final String KEY_BookingID="bookingid";



    private static UserSession objInstance;
    public static UserSession getInstance(Context context) {
        if (objInstance == null) {
            objInstance = new UserSession(context);
        }
        return objInstance;
    }

    // Constructor
    public UserSession(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    //Create login session
    public void createUserLoginSession(String userid,String mobileno) {
        // Storing login value as TRUE
        editor.putBoolean(IS_USER_LOGIN, true);

        editor.putString(KEY_USERID,userid);
        // Storing phno in preferences
        editor.putString(KEY_MOBILENO, mobileno);
        // Storing email in preferences

        editor.commit();
    }

    //Storing User Address
    public void SaveAddress(String flatNo,String localityArea,String city,String state,String country,String pinCode){
        editor.putString(KEY_AddressFlatNo, flatNo);
        editor.putString(KEY_AddressLocalityArea, localityArea);
        editor.putString(KEY_AddressCity, city);
        editor.putString(KEY_AddressState, state);
        editor.putString(KEY_Country, country);
        editor.putString(KEY_AddressPostalCode, pinCode);

        editor.commit();
    }

    //Getting User Address
    public HashMap<String, String> getAddress() {
        HashMap<String, String> userAddress = new HashMap<String, String>();
        try {
            userAddress.put(KEY_AddressFlatNo, pref.getString(KEY_AddressFlatNo, ""));
            userAddress.put(KEY_AddressLocalityArea, pref.getString(KEY_AddressLocalityArea, ""));
            userAddress.put(KEY_AddressCity, pref.getString(KEY_AddressCity, ""));
            userAddress.put(KEY_AddressState, pref.getString(KEY_AddressState, ""));
            userAddress.put(KEY_AddressPostalCode, pref.getString(KEY_AddressPostalCode, ""));
            userAddress.put(KEY_Country, pref.getString(KEY_Country, ""));

            UiAppConstant.area=userAddress.get(KEY_AddressFlatNo);
            UiAppConstant.place=userAddress.get(KEY_AddressLocalityArea);
            UiAppConstant.city=userAddress.get(KEY_AddressCity);
            UiAppConstant.state=userAddress.get(KEY_AddressState);
            UiAppConstant.country=userAddress.get(KEY_Country);
            UiAppConstant.postcode=userAddress.get(KEY_AddressPostalCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return userAddress;
    }

    /**
     * Check login method will check user login status
     * If false it will redirect user to login page
     * Else do anything
     */
    public boolean checkLogin() {
        // Check login status
        if (!this.isUserLoggedIn()) {

            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, ActivityLogin.class);

            // Closing all the Activities from stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);

            return true;
        }
        return false;
    }


    /**
     * Get stored session data
     */
    public HashMap<String, String> getUserDetails() {

        //Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<String, String>();

        user.put(KEY_USERID, pref.getString(KEY_USERID, ""));
        user.put(KEY_MOBILENO, pref.getString(KEY_MOBILENO, ""));


        // return user
        return user;
    }

    /**
     * Clear session details
     */
    public static void logoutUser() {

        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();

        /*// After logout redirect user to MainActivity
        Intent i = new Intent(_context, SplashActivity.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);*/
    }

    // Check for login
    public boolean isUserLoggedIn() {
        return pref.getBoolean(IS_USER_LOGIN, false);
    }


    // Check for language selection
    public void SetLanguageSelection(boolean isselected){
        editor.putBoolean(KEY_IsLanguageSelected,isselected);
        editor.commit();
    }
    public boolean isLanguageSelected() {
        return pref.getBoolean(KEY_IsLanguageSelected, false);
    }

    //Selected language
    public void SaveSelectedLanguage(String language){
        editor.putString(KEY_SelectedLanguage,language);
        editor.commit();
    }

    public String GetSelectedLanguage(){
        return pref.getString(KEY_SelectedLanguage,"");
    }

    public void SaveToken(String token){
        editor.putString(KEY_TOKEN,token);
        editor.commit();
    }

    public String getToken(){
        return pref.getString(KEY_TOKEN,"");
    }

    public void SaveNotificationRegID(String regid){
        editor.putString(KEY_NotificationRegID,regid);
        editor.commit();
    }

    public String getNotificationRegID(){
        return pref.getString(KEY_NotificationRegID,"");
    }

    public void SaveBookingID(String regid){
        editor.putString(KEY_BookingID,regid);
        editor.commit();
    }

    public String getBookingID(){
        return pref.getString(KEY_BookingID,"");
    }
}
