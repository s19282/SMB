package com.example.smb_p01

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smb_p01.databinding.ShopBinding

class FavShopAdapter :
    RecyclerView.Adapter<FavShopAdapter.ViewHolder>() {

    class ViewHolder(val binding: ShopBinding) : RecyclerView.ViewHolder(binding.root)

    private var favShops = mutableListOf<FavShop>()

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

    @SuppressLint("NotifyDataSetChanged")
    fun add(favShop: FavShop) {
        favShops.add(favShop)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setPoints(shops: MutableList<FavShop>) {
        favShops = shops
        notifyDataSetChanged()
    }
}