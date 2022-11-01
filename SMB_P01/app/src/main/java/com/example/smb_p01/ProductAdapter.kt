package com.example.smb_p01

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smb_p01.databinding.ProductBinding

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
        holder.binding.priceTextView.text = products[position].price.toString()
        holder.binding.amountValueTextView.text = products[position].amount.toString()
        holder.binding.isBoughtCheckBox.isChecked = products[position].isBought

        holder.binding.root.setOnClickListener {
            delete(products[position].id)
        }
    }

    override fun getItemCount(): Int = products.size

    fun add(product: Product) {
        pvm.insert(product)
        notifyDataSetChanged() //TODO: replace with notifyItemInserted
    }

    fun delete(id: Long) {
        pvm.delete(id)
        notifyDataSetChanged()
    }

    fun setProducts(allProducts: List<Product>) {
        products = allProducts
        notifyDataSetChanged()
    }
}