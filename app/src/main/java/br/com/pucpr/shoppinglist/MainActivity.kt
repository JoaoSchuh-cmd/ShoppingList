package br.com.pucpr.shoppinglist

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.pucpr.shoppinglist.data.ShoppingList
import br.com.pucpr.shoppinglist.view.ItemListScreen
import br.com.pucpr.shoppinglist.view.NewItemScreen
import br.com.pucpr.shoppinglist.view.ShoppingListScreen
import br.com.pucpr.shoppinglist.viewmodel.ShoppingListViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val shoppingListViewModel: ShoppingListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                val gson = Gson()

                NavHost(navController, startDestination = "shoppingList") {
                    composable("shoppingList") {
                        ShoppingListScreen(shoppingListViewModel) { selectedList ->
                            val shoppingListJson = Uri.encode(gson.toJson(selectedList))
                            navController.navigate("items/$shoppingListJson")
                        }
                    }

                    composable(
                        route = "items/{shoppingList}",
                        arguments = listOf(navArgument("shoppingList") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val shoppingListJson = backStackEntry.arguments?.getString("shoppingList")
                        val shoppingList = gson.fromJson(shoppingListJson, ShoppingList::class.java)
                        ItemListScreen(
                            viewModel = shoppingListViewModel,
                            shoppingList = shoppingList,
                            onBack = { navController.popBackStack() },
                            onAddItem = { listId ->
                                navController.navigate("newItem/$listId")
                            }
                        )
                    }
                    composable("newItem/{listId}") { backStackEntry ->
                        val listId = backStackEntry.arguments?.getString("listId")?.toInt() ?: -1
                        NewItemScreen (shoppingListViewModel, listId) { navController.popBackStack() }
                    }
                }
            }
        }
    }
}
