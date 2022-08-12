package com.danp.vierdesjet.workers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.danp.vierdesjet.R
import com.danp.vierdesjet.room.user.UserApp
import com.danp.vierdesjet.service.channelId
import com.danp.vierdesjet.service.channelName

class NotificationA(context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {
    private val TAG: String = "NotificationA"

    val context = context
    val userApp = UserApp(context)

    override suspend fun doWork(): Result {
        val userCode = inputData.getString(KEY_CODE_ARG)

        if(userCode != null && userApp.room.userDao().getNotification(userCode)!=null && !userApp.room.userDao().getNotification(userCode)){
            var plantsIrrigatedA = userApp.room.userDao().getPlantsIrrigatedA(userCode)
            val channel_id = 0
            val channel_name = "recordatorio"
            var builder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.plant_icon)
                .setContentTitle("Recordatorio")
                .setContentText("Plantas regadas durante el dia: $plantsIrrigatedA")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                val notificationChannel =
                    NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
                notificationManager.createNotificationChannel(notificationChannel)
            }
            notificationManager.notify(channel_id,builder.build())

            Log.d("Prueba", "Notificacion A enviada")

            return Result.success()
        }

        return Result.failure()
    }
}