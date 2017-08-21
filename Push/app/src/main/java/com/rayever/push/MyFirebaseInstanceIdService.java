package com.rayever.push;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by kenny on 2017-08-11.
 */

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService
{
    private static final String TAG = "Push";

    public void onTokenRefresh()
    {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token)
    {
        Log.d(TAG, "send Server");
    }
}
