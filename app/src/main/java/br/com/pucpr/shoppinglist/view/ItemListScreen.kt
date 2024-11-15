package br.com.pucpr.shoppinglist.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.pucpr.shoppinglist.data.ShoppingList
import br.com.pucpr.shoppinglist.viewmodel.ShoppingListViewModel

@Composable
fun ItemListScreen(
    viewModel: ShoppingListViewModel,
    shoppingList: ShoppingList,
    onBack: () -> Unit,
    onAddItem: (Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = shoppingList.name,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        val items by viewModel.getItemsForList(shoppingList.id).observeAsState(emptyList())
        LazyColumn {
            items(items) { item ->
                Text(text = "${item.name} (${item.quantity})")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = onBack) {
                Text("Back")
            }
            Button(onClick = { onAddItem(shoppingList.id.toInt()) }) {
                Text("Add Item")
            }
        }
    }
}