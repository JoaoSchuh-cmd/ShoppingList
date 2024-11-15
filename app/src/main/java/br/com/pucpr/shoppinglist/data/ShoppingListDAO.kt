package br.com.pucpr.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface ShoppingListDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(shoppingList: ShoppingList)

    @Delete
    suspend fun deleteList(shoppingList: ShoppingList)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: ShoppingListItemsItem)

    @Query("SELECT * FROM shopping_list ORDER BY dateCreated DESC")
    fun getAllLists(): LiveData<List<ShoppingList>>

    @Query("SELECT * FROM shopping_list_item WHERE shoppingListId = :listId")
    fun getItemsForList(listId: Int): LiveData<List<ShoppingListItemsItem>>

    @Update
    suspend fun updateItem(newItem: ShoppingListItemsItem)
}