package br.com.pucpr.shoppinglist.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_list_item")
data class ShoppingListItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val shoppingListId: Long,
    val name: String,
    val quantity: Int = 1,
    val isChecked: Boolean = false
)
