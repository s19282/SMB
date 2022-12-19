package com.example.smb_p01

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smb_p01.databinding.ActivityShopListBinding
import com.google.gson.Gson


class ShopListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShopListBinding
    private lateinit var sp: SharedPreferences
    private var favShops = mutableListOf<MapPoint>()
    private lateinit var mapPointAdapter: MapPointAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityShopListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mapPointAdapter = MapPointAdapter()
        sp = getSharedPreferences("favShops", Context.MODE_PRIVATE)
        binding.favShopList.layoutManager = LinearLayoutManager(this)
        binding.favShopList.adapter = mapPointAdapter
        binding.favAddButton.setOnClickListener {
            val point = MapPoint(
                binding.shopName.text.toString(),
                binding.shopDescription.text.toString(),
                binding.shopRadius.text.toString().toInt(),
                1.0,
                2.0
            )
            //favShops.add(point)
            mapPointAdapter.add(point)
            val prefsEditor: SharedPreferences.Editor = sp.edit()
            val gson = Gson()
            val json = gson.toJson(favShops)
            prefsEditor.putString("favShops", json)
            prefsEditor.apply()
        }
    }

    override fun onStart() {
        super.onStart()
        val gson = Gson()
        val json = sp.getString("favShops", "")
        favShops = try{
            gson.fromJson(json, Array<MapPoint>::class.java).toList() as ArrayList<MapPoint>
        } catch (e: Exception){
            mutableListOf()
        }
        mapPointAdapter.setPoints(favShops)
    }
}