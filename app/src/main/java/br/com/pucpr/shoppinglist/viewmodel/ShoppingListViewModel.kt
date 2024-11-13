package br.com.pucpr.shoppinglist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.pucpr.shoppinglist.data.ShoppingList
import br.com.pucpr.shoppinglist.repository.ShoppingListRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShoppingListViewModel @Inject constructor(
    private val repository: ShoppingListRepository
): ViewModel() {
    val allShoppingLists: LiveData<List<ShoppingList>> = repository.allShoppingLists

    fun insert(shoppingList: ShoppingList) {
        viewModelScope.launch {
            repository.insert(shoppingList)
        }
    }

    fun delete(shoppingList: ShoppingList) {
        viewModelScope.launch {
            repository.delete(shoppingList)
        }
    }
}