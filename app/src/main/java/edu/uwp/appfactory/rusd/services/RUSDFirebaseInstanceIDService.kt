package edu.uwp.appfactory.rusd.services

import android.content.Context
import android.preference.PreferenceManager
import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

/**
 * Created by Jeremiah on 8/22/17.
 */
class RUSDFirebaseInstanceIDService: FirebaseInstanceIdService() {

    override fun onTokenRefresh() {
        val refreshedToken = FirebaseInstanceId.getInstance().token

        val authToken = getSharedPreferences("prefs", 0).getString("authToken", null)
        if (authToken != null) {
            FirebaseRegistrationTask().execute(this, refreshedToken)
        }
    }
}