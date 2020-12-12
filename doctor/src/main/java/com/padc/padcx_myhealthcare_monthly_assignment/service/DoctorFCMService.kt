package com.padc.padcx_myhealthcare_monthly_assignment.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.padc.padcx_myhealthcare_monthly_assignment.activities.LoginActivity
import com.padc.padcx_myhealthcare_monthly_assignment.activities.MainActivity
import com.padc.padcx_myhealthcare_monthly_assignment.activities.SplashScreenActivity
import com.padc.share.R

class DoctorFCMService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)


        remoteMessage !! .data.let {
            if (remoteMessage.data.isNotEmpty()){
                val body = remoteMessage.data["body"]
                val title = remoteMessage.data["title"]
               // val requestID = remoteMessage.data["consultationRequestID"]
                Log . d ("notiStatus", "recieve noti")

                showNotification(body, title,"")
            }
        }


    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }

    private fun showNotification(body: String?, title: String?,requestID : String?) {
        val intent = Intent(requestID?.let { SplashScreenActivity.newIntent(this) })
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent: PendingIntent =
                PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        val channelId = "HELLO_FCM_CHANNEL"
        val channelName = "HELLO_FCM_CHANNEL_HI"
        val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setUpNotificationChannel(channelId, channelName, notificationManager)
        }

        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder = NotificationCompat.Builder(this,channelId)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark_focused)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setPriority(android.app.Notification.PRIORITY_HIGH)
                .setSound(soundUri)
                .setContentIntent(pendingIntent)

        notificationManager.notify(0,notificationBuilder.build())
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun setUpNotificationChannel(
            channelId: String,
            channelName: String,
            notificationManager: NotificationManager
    ) {
        val channel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_LOW)
        channel.enableLights(true)
        channel.lightColor = Color.GREEN
        channel.enableVibration(true)
        notificationManager.createNotificationChannel(channel)

    }
}