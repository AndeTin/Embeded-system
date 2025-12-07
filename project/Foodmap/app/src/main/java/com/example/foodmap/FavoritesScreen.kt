package com.example.foodmap

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FavoritesScreen(
    favoriteRestaurants: List<Restaurant>,
    routePlans: List<RoutePlan>,
    onAddRestaurantToPlan: (Restaurant, String) -> Unit,
    onFavoriteClick: (Restaurant) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedRestaurant by remember { mutableStateOf<Restaurant?>(null) }

    if (favoriteRestaurants.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("No favorites yet.")
        }
    } else {
        LazyColumn {
            items(favoriteRestaurants) { restaurant ->
                RestaurantItem(
                    restaurant = restaurant, 
                    onAddToPlanClick = {
                        selectedRestaurant = restaurant
                        showDialog = true
                    },
                    onFavoriteClick = { onFavoriteClick(restaurant) }
                )
            }
        }
    }

    if (showDialog) {
        AddRestaurantToPlanDialog(
            routePlans = routePlans,
            onDismiss = { showDialog = false },
            onConfirm = { planName ->
                selectedRestaurant?.let { onAddRestaurantToPlan(it, planName) }
                showDialog = false
            }
        )
    }
}

@Composable
private fun RestaurantItem(restaurant: Restaurant, onAddToPlanClick: () -> Unit, onFavoriteClick: () -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp)) {
        Row(modifier = Modifier.padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = restaurant.name)
                Text(text = restaurant.address)
                Text(text = restaurant.cuisine)
            }
            Row {
                Button(onClick = onFavoriteClick) {
                    Text("Unfavorite")
                }
                Button(onClick = onAddToPlanClick) {
                    Text("Add to Plan")
                }
            }
        }
    }
}

@Composable
private fun AddRestaurantToPlanDialog(
    routePlans: List<RoutePlan>,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var newPlanName by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Add to Plan") },
        text = {
            Column {
                if (routePlans.isNotEmpty()) {
                    Text(text = "Select a plan:")
                    Box(modifier = Modifier.height(100.dp)) {
                        LazyColumn {
                            items(routePlans) { plan ->
                                Text(text = plan.name, modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onConfirm(plan.name) }
                                    .padding(vertical = 8.dp)
                                )
                            }
                        }
                    }
                }
                OutlinedTextField(
                    value = newPlanName,
                    onValueChange = { newPlanName = it },
                    label = { Text("Or create a new plan") }
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(newPlanName) },
                enabled = newPlanName.isNotBlank()
            ) {
                Text("Create and Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
