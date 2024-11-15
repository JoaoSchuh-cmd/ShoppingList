package br.com.pucpr.shoppinglist.view

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import br.com.pucpr.shoppinglist.data.ShoppingList
import br.com.pucpr.shoppinglist.viewmodel.ShoppingListViewModel

@Composable
fun ShoppingListScreen(
    viewModel: ShoppingListViewModel,
    onListSelected: (ShoppingList) -> Unit
) {
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
                    .padding(8.dp)
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
            items(shoppingLists) { shoppingList ->
                ShoppingListItem(
                    shoppingList = shoppingList,
                    onClick = { onListSelected(shoppingList) },
                    onDelete = { viewModel.delete(shoppingList) }
                )
            }
        }
    }
}

@Composable
fun ShoppingListItem(
    shoppingList: ShoppingList,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = shoppingList.name, modifier = Modifier.weight(1f))

        IconButton(onClick = { showDialog = true }) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete List")
        }
    }

    if (showDialog) {
        DeleteConfirmationDialog(
            onConfirm = {
                onDelete()
                showDialog = false
            },
            onDismiss = { showDialog = false }
        )
    }
}

@Composable
fun DeleteConfirmationDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Confirm Deletion") },
        text = { Text("Are you sure you want to delete this shopping list?") },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm()
                }
            ) {
                Text("Yes")
            }
        },
        dismissButton = {
            TextButton(
                onClick = { onDismiss() }
            ) {
                Text("No")
            }
        }
    )
}