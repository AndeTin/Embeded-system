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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.math.ceil

@Composable
fun SearchScreen(restaurants: List<Restaurant>, onFavoriteClick: (Restaurant) -> Unit) {
    var text by remember { mutableStateOf("") }
    var currentPage by remember { mutableIntStateOf(1) }
    val pageSize = 5

    val filteredRestaurants = if (text.isBlank()) {
        restaurants
    } else {
        restaurants.filter { it.name.contains(text, ignoreCase = true) }
    }

    val pageCount = ceil(filteredRestaurants.size.toFloat() / pageSize).toInt()

    val paginatedRestaurants = filteredRestaurants.chunked(pageSize).getOrElse(currentPage - 1) { emptyList() }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = text,
            onValueChange = { 
                text = it
                currentPage = 1 // Reset to first page on search
            },
            label = { Text("Search for a restaurant") },
            modifier = Modifier.fillMaxWidth()
        )
        LazyColumn(modifier = Modifier.padding(top = 8.dp).weight(1f)) {
            items(paginatedRestaurants) { restaurant ->
                RestaurantItem(restaurant = restaurant, onFavoriteClick = { onFavoriteClick(restaurant) })
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            for (i in 1..pageCount) {
                TextButton(onClick = { currentPage = i }) {
                    Text(text = i.toString())
                }
            }
        }
    }
}

@Composable
fun RestaurantItem(restaurant: Restaurant, onFavoriteClick: () -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp)) {
        Row(modifier = Modifier.padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
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