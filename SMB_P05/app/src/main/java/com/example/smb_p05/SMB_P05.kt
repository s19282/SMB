package com.example.smb_p05

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.widget.RemoteViews
import androidx.core.content.ContextCompat.startActivity
import com.example.smb_p05.SMB_P05.Companion.mediaPlayer
import com.example.smb_p05.SMB_P05.Companion.songId


/**
 * Implementation of App Widget functionality.
 */
class SMB_P05 : AppWidgetProvider() {
    companion object {
        var imageSwitch = true
        var musicSwitch = true
        var playPause = true
        var songId = R.raw.moda
        lateinit var mediaPlayer: MediaPlayer
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)

        if (intent?.action == "com.example.smb_p05.SMB_P05") {
            val openWebsite = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.pja.edu.pl/"))
            openWebsite.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context?.startActivity(
                openWebsite
            )
        }
        if (intent?.action == "com.example.smb_p05.SMB_P05_switchImage") {
            val avm = AppWidgetManager.getInstance(context)
            val remoteViews = RemoteViews(context?.packageName, R.layout.s_m_b__p05)
            imageSwitch = if (imageSwitch) {
                remoteViews.setImageViewResource(R.id.image, R.drawable.apple)
                false
            } else {
                remoteViews.setImageViewResource(R.id.image, R.drawable.android)
                true
            }
            avm.updateAppWidget(
                Integer.parseInt(intent.getStringExtra("id").orEmpty()),
                remoteViews
            )

        }
        if (intent?.action == "com.example.smb_p05.SMB_P05_music_playPause" && context != null) {
            val avm = AppWidgetManager.getInstance(context)
            val remoteViews = RemoteViews(context.packageName, R.layout.s_m_b__p05)
            playPause = if (playPause) {
                remoteViews.setImageViewResource(R.id.playPause, android.R.drawable.ic_media_pause)
                mediaPlayer.start()
                false
            } else {
                remoteViews.setImageViewResource(R.id.playPause, android.R.drawable.ic_media_play)
                mediaPlayer.pause()
                true
            }
            avm.updateAppWidget(
                Integer.parseInt(intent.getStringExtra("id").orEmpty()),
                remoteViews
            )
        }
        if (intent?.action == "com.example.smb_p05.SMB_P05_music_next" && context != null) {
            musicSwitch = if (musicSwitch) {
                songId = R.raw.serpentine
                false
            } else {
                songId = R.raw.moda
                true
            }
            mediaPlayer.release()
            mediaPlayer = MediaPlayer.create(context, songId)
        }
        if (intent?.action == "com.example.smb_p05.SMB_P05_music_stop" && context != null) {
            mediaPlayer.stop()
            mediaPlayer = MediaPlayer.create(context, songId)
        }
        if (intent?.action == "com.example.smb_p05.SMB_P05_openFavList" && context != null) {
            val externalIntent = Intent()
            externalIntent.component = ComponentName(
                "com.example.smb_p01",
                "com.example.smb_p01.FavShopListActivity"
            )
            externalIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(context, externalIntent, Bundle())
        }
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val views = RemoteViews(context.packageName, R.layout.s_m_b__p05)
    mediaPlayer = MediaPlayer.create(context, songId)

    val intentClick = Intent()
    intentClick.action = "com.example.smb_p05.SMB_P05"
    intentClick.component = ComponentName(context, SMB_P05::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        1,
        intentClick,
        PendingIntent.FLAG_IMMUTABLE
    )

    val intentClick2 = Intent()
    intentClick2.action = "com.example.smb_p05.SMB_P05_switchImage"
    intentClick2.component = ComponentName(context, SMB_P05::class.java)
    intentClick2.putExtra("id", appWidgetId.toString())

    val pendingIntent2 = PendingIntent.getBroadcast(
        context,
        2,
        intentClick2,
        PendingIntent.FLAG_IMMUTABLE
    )
    val intentClick3 = Intent()
    intentClick3.action = "com.example.smb_p05.SMB_P05_openFavList"
    intentClick3.component = ComponentName(context, SMB_P05::class.java)
    intentClick3.putExtra("id", appWidgetId.toString())

    val pendingIntent3 = PendingIntent.getBroadcast(
        context,
        3,
        intentClick3,
        PendingIntent.FLAG_IMMUTABLE
    )
    val intentClick4a = Intent()
    intentClick4a.putExtra("id", appWidgetId.toString())
    intentClick4a.action = "com.example.smb_p05.SMB_P05_music_playPause"
    intentClick4a.component = ComponentName(context, SMB_P05::class.java)
    val pendingIntent4a = PendingIntent.getBroadcast(
        context,
        4,
        intentClick4a,
        PendingIntent.FLAG_IMMUTABLE
    )
    val intentClick4b = Intent()
    intentClick4b.action = "com.example.smb_p05.SMB_P05_music_next"
    intentClick4b.component = ComponentName(context, SMB_P05::class.java)
    val pendingIntent4b = PendingIntent.getBroadcast(
        context,
        5,
        intentClick4b,
        PendingIntent.FLAG_IMMUTABLE
    )
    val intentClick4c = Intent()
    intentClick4c.action = "com.example.smb_p05.SMB_P05_music_stop"
    intentClick4c.component = ComponentName(context, SMB_P05::class.java)
    val pendingIntent4c = PendingIntent.getBroadcast(
        context,
        6,
        intentClick4c,
        PendingIntent.FLAG_IMMUTABLE
    )
    views.setOnClickPendingIntent(R.id.playPause, pendingIntent4a)
    views.setOnClickPendingIntent(R.id.next, pendingIntent4b)
    views.setOnClickPendingIntent(R.id.stop, pendingIntent4c)
    views.setOnClickPendingIntent(R.id.openIntent, pendingIntent3)
    views.setOnClickPendingIntent(R.id.openWebsiteButton, pendingIntent)
    views.setOnClickPendingIntent(R.id.image, pendingIntent2)
    appWidgetManager.updateAppWidget(appWidgetId, views)
}