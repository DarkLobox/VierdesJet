package com.danp.vierdesjet.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.danp.vierdesjet.DataStoreManager
import com.danp.vierdesjet.room.plant.PlantApp
import com.danp.vierdesjet.room.user.UserApp
import java.text.SimpleDateFormat
import java.util.*

const val KEY_RESET_ARG = "KEY_RESET_ARG"
const val KEY_CODE_ARG = "KEY_CODE_ARG"

class ResetWorker(context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {
    private val TAG: String = "ResetWorker"

    val plantApp = PlantApp(context)
    val dataStore = DataStoreManager(context)
    val userApp = UserApp(context)

    override suspend fun doWork(): Result {
        val dateReset = inputData.getString(KEY_RESET_ARG)
        val userCode = inputData.getString(KEY_CODE_ARG)
        if (dateReset != null && userCode != null) {
            val dateTemp = SimpleDateFormat("dd/M/yyyy").format(Date())
            if(dateReset.compareTo(dateTemp)!=0) {
                try {
                    plantApp.room.plantDao().resetAllA(userCode)
                    plantApp.room.plantDao().resetAllB(userCode)
                    plantApp.room.plantDao().resetAllC(userCode)
                    dataStore.setDateReset(dateTemp)
                    userApp.room.userDao().setPlantsIrrigated(0,userCode)
                    userApp.room.userDao().setPlantsIrrigatedA(0,userCode)
                    userApp.room.userDao().setPlantsIrrigatedB(0,userCode)
                    userApp.room.userDao().setPlantsIrrigatedC(0,userCode)
                    userApp.room.userDao().setNotification(false,userCode)
                } catch (e: Exception) { }
            }
        }
        return Result.success()
    }
}