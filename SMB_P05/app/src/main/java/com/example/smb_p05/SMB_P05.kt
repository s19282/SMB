package com.example.smb_p05

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews

/**
 * Implementation of App Widget functionality.
 */
class SMB_P05 : AppWidgetProvider() {
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
    views.setOnClickPendingIntent(R.id.openWebsiteButton, pendingIntent)
    appWidgetManager.updateAppWidget(appWidgetId, views)
}