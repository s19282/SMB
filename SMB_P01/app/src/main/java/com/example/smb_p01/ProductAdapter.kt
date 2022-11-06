package com.example.smb_p01

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smb_p01.databinding.ProductBinding
import kotlinx.coroutines.DelicateCoroutinesApi
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

    @OptIn(DelicateCoroutinesApi::class)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.nameValueTextView.text = products[position].name
        holder.binding.priceTextView.text = products[position].price.toString()
        holder.binding.amountValueTextView.text = products[position].amount.toString()
        holder.binding.isBoughtCheckBox.isChecked = products[position].isBought

        holder.binding.root.setOnClickListener {
            CoroutineScope(IO).launch {
                delete(products[position].id)
            }
        }
    }

    override fun getItemCount(): Int = products.size

    suspend fun add(product: Product) {
        CoroutineScope(IO).launch{
            pvm.insert(product)
        }
        notifyDataSetChanged() //TODO: replace with notifyItemInserted
    }

    suspend fun delete(id: Long) {
        CoroutineScope(IO).launch {
            pvm.delete(id)
        }
        notifyDataSetChanged()
    }

    suspend fun setProducts(allProducts: List<Product>) {
        CoroutineScope(IO).launch {
            products = allProducts
        }
        notifyDataSetChanged()
    }
}