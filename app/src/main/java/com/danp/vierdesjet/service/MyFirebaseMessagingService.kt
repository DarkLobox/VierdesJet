package com.danp.vierdesjet.service

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.danp.vierdesjet.MainActivity
import com.danp.vierdesjet.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
const val channelId = "notificaion_chanel"
const val channelName = "com.danp.vierdesjet.service"
class MyFirebaseMessagingService : FirebaseMessagingService() {
    companion object {
        private const val TAG = "FCM Notification"
        const val DEFAULT_NOTIFICATION_ID = 0
    }

    override fun onNewToken(token: String) {
        Log.i(TAG, "new FCM token created: $token")

    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if(remoteMessage.notification!=null){
            Log.d("Enviado","Message recibido" + remoteMessage.data)
            generateNotification(remoteMessage.notification!!.title!!,remoteMessage.notification!!.body!!)
        }
    }
    //creando notificacion con su diseño personalizado
    @SuppressLint("RemoteViewLayout")
    fun getRemoteView(titulo: String, mensaje: String): RemoteViews {
        val remoteView = RemoteViews("com.danp.vierdesjet", R.layout.notification)
        remoteView.setTextViewText(R.id.titulo, titulo)
        remoteView.setTextViewText(R.id.mensaje, mensaje)
        remoteView.setImageViewResource(R.id.logo, R.drawable.regando_plantas)

        return remoteView
    }
    //ver notificacion
    fun generateNotification(titulo: String, mensaje: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        //Para trabajar este apartado se trabajó con Intent y pendingIntent para asignarle las acciones a los botones
        val snoozeIntent = Intent(this, MainActivity::class.java).apply {
            putExtra(Notification.EXTRA_NOTIFICATION_ID, 0)
        }
        val snoozePendingIntent1: PendingIntent =
            PendingIntent.getBroadcast(this, 0, snoozeIntent, 0)
        val snoozePendingIntent2: PendingIntent =
            PendingIntent.getBroadcast(this, 0, snoozeIntent, 0)

        var builder: NotificationCompat.Builder = NotificationCompat.Builder(
            applicationContext, channelId)
            .setSmallIcon(R.drawable.regando_plantas)
            .setAutoCancel(true)
            .setContentTitle(titulo)
            .setContentText(mensaje)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)
            .addAction(R.drawable.regando_plantas, "<|", snoozePendingIntent1) // #0
            .addAction(R.drawable.regando_plantas, "[]", snoozePendingIntent2)  // #1


        builder = builder.setContent(getRemoteView(titulo, mensaje))

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationManager.notify(0,builder.build())
    }
}