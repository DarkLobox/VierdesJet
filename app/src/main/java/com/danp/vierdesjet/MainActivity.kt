package com.danp.vierdesjet

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.danp.vierdesjet.navigation.NavigationHost
import com.danp.vierdesjet.ui.theme.VierdesJetTheme
import com.danp.vierdesjet.workers.ResetWorker
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            VierdesJetTheme {
                NavigationHost()
                FirebaseMessaging.getInstance().token
                    .addOnCompleteListener(OnCompleteListener { task ->
                        if (!task.isSuccessful) {
                            Log.d("FCM Notify", "Fetching FCM registration token failed", task.exception)
                            return@OnCompleteListener
                        }

                        //Get new FCM registration token
                        val token: String? = task.result
                        Log.d("FCM Token", token, task.exception)
                        //Toast.makeText(this, token, Toast.LENGTH_SHORT).show()

                    })
            }
        }
    }
}
