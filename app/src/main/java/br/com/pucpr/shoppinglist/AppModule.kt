package br.com.pucpr.shoppinglist

import android.content.Context
import br.com.pucpr.shoppinglist.data.ShoppingListDAO
import br.com.pucpr.shoppinglist.data.ShoppingListDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ShoppingListDatabase {
        return ShoppingListDatabase.getInstance(context)
    }

    @Provides
    fun provideShoppingListDao(database: ShoppingListDatabase): ShoppingListDAO {
        return database.shoppingListDao()
    }
}