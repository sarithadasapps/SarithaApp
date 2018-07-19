package com.iprismtech.healthyhome.httplibrary;

/**
 * Created by mustufak on 01-03-2015.
 */
public interface HttpResponses {
    public void onSuccess(String responseString, int identifier);
    public void onFailure();
}
