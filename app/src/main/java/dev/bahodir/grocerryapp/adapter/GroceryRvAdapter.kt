package dev.bahodir.grocerryapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.bahodir.grocerryapp.databinding.ActivityMainBinding
import dev.bahodir.grocerryapp.databinding.MainRvItemBinding
import dev.bahodir.grocerryapp.room.GroceryItems

class GroceryRvAdapter(var list: List<GroceryItems>, var click: GroceryItemClick) :
    RecyclerView.Adapter<GroceryRvAdapter.VH>() {

    interface GroceryItemClick {
        fun onItemClick(items: GroceryItems, position: Int)
    }

    inner class VH(var binding: MainRvItemBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(items: GroceryItems, position: Int) {
            binding.tvItemName.text = items.itemName
            binding.tvQuantity.text = items.itemQuantity.toString()
            binding.tvRate.text = "Rs. " + items.itemPrice.toString()
            binding.tvTotalAmt.text = (items.itemPrice * items.itemQuantity).toString()

            binding.ivDelete.setOnClickListener {
                click.onItemClick(items, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(MainRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(list[position], position)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}