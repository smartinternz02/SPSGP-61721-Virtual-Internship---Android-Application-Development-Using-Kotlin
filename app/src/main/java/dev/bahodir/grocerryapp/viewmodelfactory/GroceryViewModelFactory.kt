package dev.bahodir.grocerryapp.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.bahodir.grocerryapp.repository.GroceryRepository
import dev.bahodir.grocerryapp.viewmodel.GroceryViewModel

class GroceryViewModelFactory(private var repository: GroceryRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GroceryViewModel(repository) as T
    }
}