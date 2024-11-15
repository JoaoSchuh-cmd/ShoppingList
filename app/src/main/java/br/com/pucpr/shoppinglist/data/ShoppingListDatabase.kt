package br.com.pucpr.shoppinglist.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ShoppingList::class, ShoppingListItem::class], version = 1)
abstract class ShoppingListDatabase: RoomDatabase() {

    abstract fun shoppingListDao(): ShoppingListDAO

    companion object {
        @Volatile private var instance: ShoppingListDatabase? = null

        fun getInstance(context: Context): ShoppingListDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    ShoppingListDatabase::class.java,
                    "shopping_database"
                )
                    .fallbackToDestructiveMigration()
                    .build().also { instance = it }
            }
        }
    }
}