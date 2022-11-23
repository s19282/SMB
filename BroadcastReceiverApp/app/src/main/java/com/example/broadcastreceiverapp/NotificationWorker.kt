package com.example.broadcastreceiverapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlin.random.Random


class NotificationWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {
    companion object {
        var counter = Random.nextInt()
    }

    override fun doWork(): Result {
        val channel = NotificationManagerCompat
            .from(applicationContext)
            .getNotificationChannel("ProductAddChannel")
        val channelId: String =
            if (channel != null) channel.id else createChannel(applicationContext)
        val intent = Intent()
        intent.component =
            ComponentName("com.example.smb_p01", "com.example.smb_p01.ProductActivity")
        intent.putExtra("mode", "Edit")
        intent.putExtra("id", inputData.getLong("id", 0))
        intent.putExtra("name", inputData.getString("name"))
        intent.putExtra("price", inputData.getString("price"))
        intent.putExtra("amount", inputData.getString("amount"))
        intent.putExtra("isBought", inputData.getBoolean("isBought", false))

        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            counter++,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat
            .Builder(applicationContext, channelId)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle("Added: ${inputData.getString("name")}")
            .setContentText(
                "price: ${inputData.getString("price")} " +
                        "amount: ${inputData.getString("amount")} " +
                        "is bought: ${inputData.getBoolean("isBought", false)}"
            )
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat
            .from(applicationContext)
            .notify(0, notification)

        return Result.success()
    }

    private fun createChannel(context: Context): String {
        val channelId = "ProductAddChannel"
        val channel = NotificationChannel(
            channelId,
            "Product Add Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        NotificationManagerCompat.from(context).createNotificationChannel(channel)
        return channelId
    }
}