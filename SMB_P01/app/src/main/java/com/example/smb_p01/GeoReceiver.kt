package com.example.smb_p01

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent

class GeoReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.i("geofenceApp","onReceive")
        when (GeofencingEvent.fromIntent(intent)!!.geofenceTransition) {
            Geofence.GEOFENCE_TRANSITION_ENTER -> {
                Toast.makeText(context, "Weszliśmy w obszar.", Toast.LENGTH_SHORT).show()
                Log.i("geofenceApp", "Weszliśmy w obszar.")
            }
            Geofence.GEOFENCE_TRANSITION_EXIT -> {
                Toast.makeText(context, "Wyszliśmy z obszaru.", Toast.LENGTH_SHORT).show()
                Log.i("geofenceApp", "Wyszliśmy z obszaru.")
            }
            else -> {
                Log.e("geofenceApp", "Wrong transition type.")
            }
        }
    }
}