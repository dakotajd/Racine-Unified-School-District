package edu.uwp.appfactory.rusd.services

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import edu.uwp.appfactory.rusd.data.local.NotificationsRepository

/**
 * Created by Jeremiah on 8/22/17.
 */

class RUSDFirebaseMessagingService: FirebaseMessagingService() {

    private val TAG = "MessagingService"


    override fun onMessageReceived(message: RemoteMessage?) {
        Log.d(TAG, "Message Received From: " + message?.from)
        if (message?.notification != null) {
            // TODO find out if we should add notification to drawer

            if (RUSDFirebaseMessagingService.notificationRepo != null) {
                RUSDFirebaseMessagingService.notificationRepo?.updateNotifications()
            }

        }
    }

    companion object {
        @JvmStatic
        private var notificationRepo: NotificationsRepository? = null

        @JvmStatic
        fun setNotificationRepository(notificationsRepository: NotificationsRepository?) {
            this.notificationRepo = notificationsRepository
        }
    }
}