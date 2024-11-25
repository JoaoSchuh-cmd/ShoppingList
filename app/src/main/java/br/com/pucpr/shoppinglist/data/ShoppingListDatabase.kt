package br.com.pucpr.shoppinglist.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.pucpr.shoppinglist.Converters

@Database(entities = [ShoppingList::class, ShoppingListItemsItem::class], version = 1)
@TypeConverters(Converters::class)
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