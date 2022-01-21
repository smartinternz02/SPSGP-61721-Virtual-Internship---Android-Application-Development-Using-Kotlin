package dev.bahodir.grocerryapp

import android.annotation.SuppressLint
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import dev.bahodir.grocerryapp.adapter.GroceryRvAdapter
import dev.bahodir.grocerryapp.databinding.ActivityMainBinding
import dev.bahodir.grocerryapp.databinding.MainAddDialogBinding
import dev.bahodir.grocerryapp.repository.GroceryRepository
import dev.bahodir.grocerryapp.room.GroceryDatabase
import dev.bahodir.grocerryapp.room.GroceryItems
import dev.bahodir.grocerryapp.viewmodel.GroceryViewModel
import dev.bahodir.grocerryapp.viewmodelfactory.GroceryViewModelFactory
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
class MainActivity : AppCompatActivity(), GroceryRvAdapter.GroceryItemClick {
    private lateinit var binding: ActivityMainBinding
    private lateinit var list: ArrayList<GroceryItems>
    private lateinit var groceryRvAdapter: GroceryRvAdapter
    @DelicateCoroutinesApi
    private lateinit var groceryViewModel: GroceryViewModel

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        list = ArrayList()
        groceryRvAdapter = GroceryRvAdapter(list, this)
        binding.rvItems.adapter = groceryRvAdapter

        val groceryRepository = GroceryRepository(GroceryDatabase(this))
        val factory = GroceryViewModelFactory(groceryRepository)

        groceryViewModel = ViewModelProvider(this, factory)[GroceryViewModel::class.java]
        groceryViewModel.getAllGroceryItems().observe(this, {
            groceryRvAdapter.list = it
            groceryRvAdapter.notifyDataSetChanged()
        })

        binding.fabAdd.setOnClickListener {
            openDialog()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun openDialog() {
        val dialog =  Dialog(this)
        val bind = MainAddDialogBinding.inflate(layoutInflater)

        dialog.setContentView(bind.root)

        bind.cancelButton.setOnClickListener {
            dialog.dismiss()
        }
        bind.addButton.setOnClickListener {
            if (bind.etItemName.text?.isNotEmpty() == true && bind.etItemQuantity.text?.isNotEmpty() == true && bind.etItemPrice.text?.isNotEmpty() == true) {

                val items = GroceryItems(bind.etItemName.text?.toString().toString(),
                   bind.etItemQuantity.text?.toString()?.toInt()!!,
                   bind.etItemPrice.text?.toString()?.toInt()!!
               )
                groceryViewModel.insert(items)
                Toast.makeText(applicationContext, "Item Inserted...", Toast.LENGTH_SHORT).show()
                groceryRvAdapter.notifyDataSetChanged()

                dialog.dismiss()
            }
            else {
                Toast.makeText(applicationContext, "Please enter all the data...", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onItemClick(items: GroceryItems, position: Int) {
        groceryViewModel.delete(items)
        groceryRvAdapter.notifyDataSetChanged()
        Toast.makeText(applicationContext, "Item Deleted...", Toast.LENGTH_SHORT).show()
    }
}