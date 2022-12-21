package com.example.smb_p01

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smb_p01.databinding.ActivityShopListBinding
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager


class ShopListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShopListBinding
    private lateinit var sp: SharedPreferences
    private var favShops = mutableListOf<MapPoint>()
    private lateinit var mapPointAdapter: MapPointAdapter
    private lateinit var permissionsManager: PermissionsManager
    private lateinit var locationManager: LocationManager
    private lateinit var geoClient: GeofencingClient

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityShopListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        geoClient = LocationServices.getGeofencingClient(this)
        mapPointAdapter = MapPointAdapter()
        sp = getSharedPreferences("favShops", Context.MODE_PRIVATE)
        binding.favShopList.layoutManager = LinearLayoutManager(this)
        binding.favShopList.adapter = mapPointAdapter
        binding.favAddButton.setOnClickListener {
            val location = getLocation()
            val point = MapPoint(
                binding.shopName.text.toString(),
                binding.shopDescription.text.toString(),
                binding.shopRadius.text.toString().toInt(),
                location!!.latitude,
                location.longitude
            )
            val geofence = Geofence
                .Builder()
                .setCircularRegion(location.latitude,location.longitude,point.radius+0.0f)
                .setExpirationDuration(30*60*1000)
                .setRequestId("geo_${point.name}")
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT)
                .build()

            val geoReq = GeofencingRequest
                .Builder()
                .addGeofence(geofence)
                .build()
            val pendingIntent = PendingIntent.getBroadcast(
                this,
                1,
                Intent(this,GeoReceiver::class.java),
                PendingIntent.FLAG_MUTABLE
            )
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) { }
            geoClient.addGeofences(geoReq,pendingIntent)
                .addOnSuccessListener {
                    Log.i("geofenceApp", "Geofence: ${geofence.requestId}  is added!")
                }
                .addOnFailureListener {
                    Log.e("geofenceApp", it.message.toString()) //ERROR 1004 = missing ACCESS_BACKGROUND_PERMISSION
                }
            favShops.add(point)
            mapPointAdapter.add(point)
            val prefsEditor: SharedPreferences.Editor = sp.edit()
            val gson = Gson()
            val json = gson.toJson(favShops)
            prefsEditor.putString("favShops", json)
            prefsEditor.apply()
        }

        val permissionsListener: PermissionsListener = object : PermissionsListener {
            override fun onExplanationNeeded(permissionsToExplain: List<String>) {
            }

            override fun onPermissionResult(granted: Boolean) {
                if (granted) {

                } else {

                }
            }
        }

        if (!PermissionsManager.areLocationPermissionsGranted(this)) {
            permissionsManager = PermissionsManager(permissionsListener)
            permissionsManager.requestLocationPermissions(this)
        }
    }

    override fun onStart() {
        super.onStart()
        val gson = Gson()
        val json = sp.getString("favShops", "")
        favShops = try {
            gson.fromJson(json, Array<MapPoint>::class.java).toList() as ArrayList<MapPoint>
        } catch (e: Exception) {
            mutableListOf()
        }
        mapPointAdapter.setPoints(favShops)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun getLocation(): Location? {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                ), 1
            )
        }
        return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
    }
}