package com.example.broadcastreceiverapp;

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest


class ProductReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null
            && context != null
        ) {
            val data = Data.Builder()
            data.putLong("id", intent.getLongExtra("id", 0))
            data.putString("name", intent.getStringExtra("name"))
            data.putString("price", intent.getStringExtra("price"))
            data.putString("amount", intent.getStringExtra("amount"))
            data.putBoolean("isBought", intent.getBooleanExtra("isBought", false))

            val uploadWorkRequest: WorkRequest =
                OneTimeWorkRequestBuilder<NotificationWorker>()
                    .setInputData(data.build())
                    .build()
            WorkManager
                .getInstance(context)
                .enqueue(uploadWorkRequest)
        }
    }
}
