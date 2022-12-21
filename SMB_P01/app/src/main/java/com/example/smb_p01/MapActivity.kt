package com.example.smb_p01

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smb_p01.databinding.ActivityMapBinding
import com.google.gson.Gson
import com.mapbox.geojson.Point
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.layers.properties.generated.TextAnchor
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager

class MapActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMapBinding
    private lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sp = getSharedPreferences("favShops", Context.MODE_PRIVATE)

        binding.mapView2.also {
            it.getMapboxMap().loadStyleUri(
                Style.MAPBOX_STREETS
            ) { }
        }

        binding.shopList.setOnClickListener {
            startActivity(Intent(this, ShopListActivity::class.java))
        }

    }

    private fun addAnnotationToMap(title: String, latitude: Double, longitude: Double) {
        val pointAnnotationManager = binding.mapView2.annotations.createPointAnnotationManager()
        val marker = BitmapFactory.decodeResource(resources, R.drawable.red_marker)
        val scaledMarker = Bitmap.createScaledBitmap(
            marker,
            (marker.width * 0.3).toInt(),
            (marker.height * 0.3).toInt(),
            true
        )

        val paOptions = PointAnnotationOptions()
            .withPoint(Point.fromLngLat(longitude, latitude))
            .withIconImage(scaledMarker)
            .withTextAnchor(TextAnchor.TOP)
            .withTextField(title)

        pointAnnotationManager.create(paOptions)
    }

    override fun onStart() {
        super.onStart()
        val gson = Gson()
        val json = sp.getString("favShops", "")
        val listOfPoints = try {
            gson.fromJson(json, Array<MapPoint>::class.java).toList()
        } catch (e: Exception) {
            mutableListOf()
        }
        listOfPoints.forEach {
            addAnnotationToMap(it.name, it.latitude, it.longitude)
        }
    }
}