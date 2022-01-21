package dev.bahodir.grocerryapp.repository

import dev.bahodir.grocerryapp.room.GroceryDatabase
import dev.bahodir.grocerryapp.room.GroceryItems

class GroceryRepository(private var db: GroceryDatabase) {

    suspend fun insert(items: GroceryItems) = db.getGroceryDao().insert(items)
    suspend fun delete(items: GroceryItems) = db.getGroceryDao().delete(items)

    fun getAllItems() = db.getGroceryDao().getAllGroceryItems()
}