package br.com.pucpr.shoppinglist.repository

import androidx.lifecycle.LiveData
import br.com.pucpr.shoppinglist.data.ShoppingList
import br.com.pucpr.shoppinglist.data.ShoppingListDAO
import br.com.pucpr.shoppinglist.data.ShoppingListItemsItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShoppingListRepository @Inject constructor (
    private val shoppingListDao: ShoppingListDAO
) {
    val allShoppingLists: LiveData<List<ShoppingList>> = shoppingListDao.getAllLists()

    suspend fun insert(shoppingList: ShoppingList) {
        shoppingListDao.insertList(shoppingList)
    }

    suspend fun delete(shoppingList: ShoppingList) {
        shoppingListDao.deleteList(shoppingList)
    }

    suspend fun insertItem(item: ShoppingListItemsItem) {
        shoppingListDao.insertItem(item)
    }

    fun getItemsForList(listId: Int): LiveData<List<ShoppingListItemsItem>> {
       return shoppingListDao.getItemsForList(listId)
    }

    fun updateItem(newItem: ShoppingListItemsItem) {
        shoppingListDao.updateItem(newItem)
    }

}