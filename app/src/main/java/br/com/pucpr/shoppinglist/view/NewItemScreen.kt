package br.com.pucpr.shoppinglist.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import br.com.pucpr.shoppinglist.data.ShoppingListItemsItem
import br.com.pucpr.shoppinglist.viewmodel.ShoppingListViewModel

@Composable
fun NewItemScreen(
    viewModel: ShoppingListViewModel,
    shoppingListId: Int,
    onItemAdded: () -> Unit
) {
    var itemName by remember { mutableStateOf("") }
    var itemQuantity by remember { mutableStateOf("1") }
    var isError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = itemName,
            onValueChange = { itemName = it },
            label = { Text("Nome do item") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = itemQuantity,
            onValueChange = { newValue ->
                if (newValue.all { it.isDigit() }) {
                    itemQuantity = newValue
                }
            },
            label = { Text("Quantidade") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (itemName.isNotBlank() && itemQuantity.isNotBlank()) {
                    val quantity = itemQuantity.toIntOrNull() ?: 1
                    val newItem = ShoppingListItemsItem(
                        shoppingListId = shoppingListId.toLong(),
                        name = itemName,
                        quantity = quantity
                    )
                    viewModel.insertItem(newItem)
                    onItemAdded()
                } else {
                    isError = true
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Adicionar Item")
        }

        if (isError && itemName.isBlank()) {
            Text(
                text = "Por favor, insira um nome válido.",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

    }

}