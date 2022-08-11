package com.danp.vierdesjet.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.danp.vierdesjet.room.plant.PlantApp
import com.danp.vierdesjet.room.user.UserApp

class CheckB(context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {
    private val TAG: String = "CheckB"

    val plantApp = PlantApp(context)
    val userApp = UserApp(context)

    override suspend fun doWork(): Result {
        val userCode = inputData.getString(KEY_CODE_ARG)
        if (userCode != null) {
            try {
                Log.d("Prueba", "Empezando la tarea Check B")
                plantApp.room.plantDao().updateAllB(userCode)
                Log.d("Prueba", "Finalizada la tarea Check B")
            } catch (e: Exception) { }
        }
        return Result.success()
    }
}