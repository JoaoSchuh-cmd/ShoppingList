package br.com.pucpr.shoppinglist.view

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import br.com.pucpr.shoppinglist.data.ShoppingList
import br.com.pucpr.shoppinglist.viewmodel.ShoppingListViewModel

@Composable
fun ShoppingListScreen(viewModel: ShoppingListViewModel, onListSelected: (ShoppingList) -> Unit) {
    var newListName by remember { mutableStateOf(TextFieldValue("")) }
    val shoppingLists by viewModel.allShoppingLists.observeAsState(emptyList())

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Lista de Compras", style = MaterialTheme.typography.titleLarge)

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                    .padding(8.dp) // Padding interno para o conteúdo do campo de texto
            ) {
                BasicTextField(
                    value = newListName,
                    onValueChange = { newListName = it },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Button(
                onClick = {
                    if (newListName.text.isNotBlank()) {
                        viewModel.insert(ShoppingList(name = newListName.text))
                        newListName = TextFieldValue("")
                    }
                }
            ) {
                Text("Adicionar")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(shoppingLists){ shoppingList ->
                ShoppingListItem(shoppingList, onClick = { onListSelected(shoppingList) })
            }
        }
    }
}

@Composable
fun ShoppingListItem(shoppingList: ShoppingList, onClick: () -> Unit) {
    Text(
        text = shoppingList.name,
        modifier = Modifier.fillMaxWidth().padding(16.dp).clickable { onClick() },
        style = MaterialTheme.typography.bodyLarge
    )
}