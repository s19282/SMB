package com.example.smb_p01

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import kotlin.random.Random

class GeoReceiver : BroadcastReceiver() {
    companion object {
        var counter = Random.nextInt()
    }

    override fun onReceive(context: Context, intent: Intent) {
        val channel = NotificationManagerCompat
            .from(context)
            .getNotificationChannel("GeoChannel")
        val channelId: String =
            if (channel != null) channel.id else createChannel(context)

        val pendingIntent = PendingIntent.getActivity(
            context,
            counter++,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        when (GeofencingEvent.fromIntent(intent)!!.geofenceTransition) {
            Geofence.GEOFENCE_TRANSITION_ENTER -> {
                Log.i("geofenceApp", "Weszliśmy w obszar.")
                val notification = NotificationCompat
                    .Builder(context, channelId)
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setContentTitle(
                        "Jesteś w promieniu ${intent.getStringExtra("radius")}m od: ${
                            intent.getStringExtra(
                                "name"
                            )
                        }"
                    )
                    .setContentText(
                        intent.getStringExtra("description")
                    )
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .build()

                NotificationManagerCompat
                    .from(context)
                    .notify(0, notification)
            }
            Geofence.GEOFENCE_TRANSITION_EXIT -> {
                Log.i("geofenceApp", "Wyszliśmy z obszaru.")
                val notification = NotificationCompat
                    .Builder(context, channelId)
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setContentTitle("Oddaliłeś się od : ${intent.getStringExtra("name")}")
                    .setContentText(
                        intent.getStringExtra("description")
                    )
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .build()

                NotificationManagerCompat
                    .from(context)
                    .notify(0, notification)
            }
            else -> {
                Log.e("geofenceApp", "Wrong transition type.")
            }
        }
    }

    private fun createChannel(context: Context): String {
        val channelId = "GeoChannel"
        val channel = NotificationChannel(
            channelId,
            "Geo channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        NotificationManagerCompat.from(context).createNotificationChannel(channel)
        return channelId
    }
}