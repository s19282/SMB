package com.example.smb_p05

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.RemoteViews
import androidx.core.content.ContextCompat.startActivity


/**
 * Implementation of App Widget functionality.
 */
class SMB_P05 : AppWidgetProvider() {
    companion object {
        var onOff = true
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
            onOff = if (onOff) {
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
    views.setOnClickPendingIntent(R.id.openIntent, pendingIntent3)
    views.setOnClickPendingIntent(R.id.openWebsiteButton, pendingIntent)
    views.setOnClickPendingIntent(R.id.image, pendingIntent2)
    appWidgetManager.updateAppWidget(appWidgetId, views)
}