package br.com.pucpr.shoppinglist.repository

import androidx.lifecycle.LiveData
import br.com.pucpr.shoppinglist.data.ShoppingList
import br.com.pucpr.shoppinglist.data.ShoppingListDAO
import javax.inject.Inject

class ShoppingListRepository @Inject constructor(
    private val shoppingListDao: ShoppingListDAO
) {
    val allShoppingLists: LiveData<List<ShoppingList>> = shoppingListDao.getAllLists()

    suspend fun insert(shoppingList: ShoppingList) {
        shoppingListDao.insertList(shoppingList)
    }

    suspend fun delete(shoppingList: ShoppingList) {
        shoppingListDao.deleteList(shoppingList)
    }
}