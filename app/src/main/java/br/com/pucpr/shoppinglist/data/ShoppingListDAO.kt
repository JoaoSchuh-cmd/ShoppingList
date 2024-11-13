package br.com.pucpr.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

interface ShoppingListDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(shoppingList: ShoppingList)

    @Delete
    suspend fun deleteList(shoppingList: ShoppingList)

    @Query("SELECT * FROM shopping_list ORDER BY dateCreated DESC")
    fun getAllLists(): LiveData<List<ShoppingList>>
}