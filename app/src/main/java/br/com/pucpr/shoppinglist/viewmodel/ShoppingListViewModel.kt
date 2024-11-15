package br.com.pucpr.shoppinglist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.pucpr.shoppinglist.data.ShoppingList
import br.com.pucpr.shoppinglist.data.ShoppingListItem
import br.com.pucpr.shoppinglist.repository.ShoppingListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
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

    fun getItemsForList(listId: Int): LiveData<List<ShoppingListItem>> {
        return repository.getItemsForList(listId)
    }

    fun updateItem(newItem: ShoppingListItem) {
        viewModelScope.launch {
            repository.updateItem(newItem)
        }
    }
}