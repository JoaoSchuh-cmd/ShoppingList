package br.com.pucpr.shoppinglist.data

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideShoppingListDAO(database: ShoppingListDatabase): ShoppingListDAO {
        return database.shoppingListDao()
    }

    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): ShoppingListDatabase {
        return Room.databaseBuilder(
            context,
            ShoppingListDatabase::class.java,
            "shopping_list_database"
        ).build()
    }
}