package br.com.pucpr.shoppinglist.repository

import androidx.lifecycle.LiveData
import br.com.pucpr.shoppinglist.data.ShoppingList
import br.com.pucpr.shoppinglist.data.ShoppingListDAO
import br.com.pucpr.shoppinglist.data.ShoppingListItem
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

    fun getItemsForList(listId: Int): LiveData<List<ShoppingListItem>> {
       return shoppingListDao.getItemsForList(listId)
    }

    fun updateItem(newItem: ShoppingListItem) {
        shoppingListDao.updateItem(newItem)
    }
}