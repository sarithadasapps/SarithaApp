package com.prog.logicprog.petdeal.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.prog.logicprog.petdeal.R;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

import utils.ApplicationConstants;
import utils.ApplicationUtils;

/**
 * Created by pramod on 27/12/2016.
 */
public class Splash extends Activity {
    String registration, loginstatus;
    CircularProgressView progressView;
    ApplicationUtils au = new ApplicationUtils();
    String email,firstname,lastname,phno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        firstname=au.getsharedpreferencesString(Splash.this,getString(R.string.app_name), ApplicationConstants.fname);
        lastname=au.getsharedpreferencesString(Splash.this,getString(R.string.app_name), ApplicationConstants.lname);
        phno=au.getsharedpreferencesString(Splash.this,getString(R.string.app_name), ApplicationConstants.phno);
        email=au.getsharedpreferencesString(Splash.this,getString(R.string.app_name), ApplicationConstants.email);
        loginstatus = au.getsharedpreferencesString(Splash.this, getString(R.string.app_name), ApplicationConstants.loginstatus);
        progressView = (CircularProgressView) findViewById(R.id.progress_view);
        progressView.startAnimation();
        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (loginstatus.equals("logindone")||loginstatus.equals("registrationdone")) {
                        Intent intent = new Intent(Splash.this, homescreen.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        Splash.this.startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();
                       // au.setsharedpreferencesString(Splash.this, getString(R.string.app_name), ApplicationConstants.email,email);
                        Log.v("address_splash ","firstname "+firstname+" lastname "+lastname+" email "+email+
                                " phno "+phno);
                    } else if (loginstatus.equals("skip")){

                        Intent intent1 = new Intent(Splash.this, homescreen.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        Splash.this.startActivity(intent1);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();
                    }else{
                         au.setsharedpreferencesString(Splash.this, getString(R.string.app_name), ApplicationConstants.loginstatus,"skip");
                        au.setsharedpreferencesString(Splash.this,getString(R.string.app_name), ApplicationConstants.email,"");
                        au.setsharedpreferencesString(Splash.this,getString(R.string.app_name), ApplicationConstants.pwd,"");
                        au.setsharedpreferencesString(Splash.this,getString(R.string.app_name), ApplicationConstants.fname,"");
                        au.setsharedpreferencesString(Splash.this,getString(R.string.app_name), ApplicationConstants.lname,"");
                        au.setsharedpreferencesString(Splash.this,getString(R.string.app_name), ApplicationConstants.phno,"");
                        au.setsharedpreferencesString(Splash.this,getString(R.string.app_name), ApplicationConstants.gender,"");
                        Intent intent1 = new Intent(Splash.this, homescreen.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        Splash.this.startActivity(intent1);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();
                    }


                }
            }
        };
        timerThread.start();

    }

    private void goToNextActivity(int animationIn, int animationOut) {
        Intent intent = new Intent(Splash.this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
        overridePendingTransition(animationIn, animationOut);
        finish();
    }


}
