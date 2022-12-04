package com.example.smb_p01

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.smb_p01.databinding.ProductBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class ProductAdapter(private val pvm: ProductViewModel) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    class ViewHolder(val binding: ProductBinding) : RecyclerView.ViewHolder(binding.root)

    private var products = emptyList<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ProductBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.nameValueTextView.text = products[position].name
        holder.binding.priceValueTextView.text = products[position].price.toString()
        holder.binding.amountValueTextView.text = products[position].amount.toString()
        holder.binding.isBoughtCheckBox.isChecked = products[position].isBought

        holder.binding.root.setOnClickListener {
            val intent = Intent(holder.binding.root.context, ProductActivity::class.java)
            intent.putExtra("mode", "Edit")
            intent.putExtra("id", products[position].id)
            intent.putExtra("name", products[position].name)
            intent.putExtra("amount", products[position].amount.toString())
            intent.putExtra("price", products[position].price.toString())
            intent.putExtra("isBought", products[position].isBought)
            ContextCompat.startActivity(
                holder.binding.root.context,
                intent,
                Bundle()
            )
        }
        holder.binding.root.setOnLongClickListener {
            AlertDialog
                .Builder(holder.binding.root.context)
                .setMessage("Are you sure you want to Delete?")
                .setPositiveButton("Yes") { _, _ ->
                    delete(products[position])
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
                .create().show()
            true
        }
    }

    override fun getItemCount(): Int = products.size

    fun add(product: Product) {
        CoroutineScope(IO).launch {
            pvm.insert(product)
        }
        notifyDataSetChanged()
    }

    fun update(product: Product){
        CoroutineScope(IO).launch {
            pvm.update(product)
        }
        notifyDataSetChanged()
    }

    private fun delete(product: Product) {
        CoroutineScope(IO).launch {
            pvm.delete(product)
        }
        notifyDataSetChanged()
    }

    fun deleteAll() {
        CoroutineScope(IO).launch {
            pvm.deleteAll()
        }
        notifyDataSetChanged()
    }

    fun setProducts(allProducts: List<Product>) {
        products = allProducts
        notifyDataSetChanged()
    }

    fun switchMode(path: String){
        products= emptyList()
        pvm.switchMode(path)
        notifyDataSetChanged()
    }
}