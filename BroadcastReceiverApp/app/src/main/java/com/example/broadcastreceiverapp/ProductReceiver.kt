package com.example.broadcastreceiverapp;

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class ProductReceiver : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null && context != null && intent.action == "com.example.smb_p01.action.AddProduct") {
            val channel = NotificationManagerCompat
                .from(context)
                .getNotificationChannel("ProductAddChannel")
            val channelId: String = if (channel != null) channel.id else createChannel(context)
            val pendingIntent = PendingIntent.getActivity(
                context,
                1,
                intent,
                PendingIntent.FLAG_MUTABLE
            )
            val notification = NotificationCompat
                .Builder(context, channelId)
                .setContentTitle("Product added:")
                .setContentText(intent.getStringExtra("name"))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()

            NotificationManagerCompat.from(context).notify(0, notification)
        }
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
