package com.example.foodmap

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SearchScreen(restaurants: List<Restaurant>, onFavoriteClick: (Restaurant) -> Unit) {
    var text by remember { mutableStateOf("") }
    val filteredRestaurants = if (text.isBlank()) {
        restaurants
    } else {
        restaurants.filter { it.name.contains(text, ignoreCase = true) }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Search for a restaurant") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = { /* Search is now live */ }, modifier = Modifier.padding(top = 8.dp)) {
            Text("Search")
        }
        LazyColumn(modifier = Modifier.padding(top = 8.dp)) {
            items(filteredRestaurants) { restaurant ->
                RestaurantItem(restaurant = restaurant, onFavoriteClick = { onFavoriteClick(restaurant) })
            }
        }
    }
}

@Composable
fun RestaurantItem(restaurant: Restaurant, onFavoriteClick: () -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp)) {
        Row(modifier = Modifier.padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text(text = restaurant.name)
                Text(text = restaurant.address)
                Text(text = restaurant.cuisine)
            }
            Button(onClick = onFavoriteClick) {
                Text(if (restaurant.isFavorite) "Unfavorite" else "Favorite")
            }
        }
    }
}