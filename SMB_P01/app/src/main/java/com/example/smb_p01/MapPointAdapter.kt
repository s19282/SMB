package com.example.smb_p01

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smb_p01.databinding.ShopBinding

class MapPointAdapter :
    RecyclerView.Adapter<MapPointAdapter.ViewHolder>() {

    class ViewHolder(val binding: ShopBinding) : RecyclerView.ViewHolder(binding.root)

    private var favShops = mutableListOf<MapPoint>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ShopBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.name.text = favShops[position].name
        holder.binding.description.text = favShops[position].description
        holder.binding.radius.text = favShops[position].radius.toString()
    }

    override fun getItemCount(): Int = favShops.size

    fun add(mapPoint: MapPoint) {
        favShops.add(mapPoint)
        notifyDataSetChanged()
    }

    fun setPoints(shops: MutableList<MapPoint>) {
        favShops = shops
        notifyDataSetChanged()
    }
}