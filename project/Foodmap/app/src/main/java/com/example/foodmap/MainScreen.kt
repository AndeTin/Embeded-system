package com.example.foodmap

import android.app.Activity
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val context = LocalContext.current

    val restaurants = remember {
        mutableStateOf(
            listOf(
                Restaurant("The Golden Spoon", "123 Main St", "Italian"),
                Restaurant("The Spicy Taco", "456 Oak Ave", "Mexican"),
                Restaurant("The Salty Squid", "789 Pine Ln", "Seafood")
            )
        )
    }

    val routePlans = remember { mutableStateOf(emptyList<RoutePlan>()) }

    val favoriteRestaurants = restaurants.value.filter { it.isFavorite }

    fun toggleFavorite(restaurant: Restaurant) {
        val index = restaurants.value.indexOf(restaurant)
        if (index != -1) {
            val updatedRestaurant = restaurant.copy(isFavorite = !restaurant.isFavorite)
            val updatedList = restaurants.value.toMutableList()
            updatedList[index] = updatedRestaurant
            restaurants.value = updatedList
        }
    }

    fun addRestaurantToPlan(restaurant: Restaurant, planName: String) {
        val plan = routePlans.value.find { it.name == planName }
        if (plan != null) {
            if (plan.restaurants.contains(restaurant)) return // Avoid duplicates
            val updatedPlan = plan.copy(restaurants = plan.restaurants + restaurant)
            val updatedPlans = routePlans.value.toMutableList()
            val planIndex = updatedPlans.indexOf(plan)
            updatedPlans[planIndex] = updatedPlan
            routePlans.value = updatedPlans
        } else {
            val newPlan = RoutePlan(planName, listOf(restaurant))
            routePlans.value = routePlans.value + newPlan
        }
    }

    fun removeRestaurantFromPlan(restaurant: Restaurant, plan: RoutePlan) {
        val updatedRestaurants = plan.restaurants.toMutableList().apply { remove(restaurant) }
        val updatedPlan = plan.copy(restaurants = updatedRestaurants)
        val updatedPlans = routePlans.value.toMutableList()
        val planIndex = updatedPlans.indexOfFirst { it.name == plan.name }
        if (planIndex != -1) {
            if (updatedRestaurants.isEmpty()) {
                updatedPlans.removeAt(planIndex)
            } else {
                updatedPlans[planIndex] = updatedPlan
            }
            routePlans.value = updatedPlans
        }
    }

    fun reorderRestaurantsInPlan(plan: RoutePlan, from: Int, to: Int) {
        val updatedRestaurants = plan.restaurants.toMutableList().apply {
            add(to, removeAt(from))
        }
        val updatedPlan = plan.copy(restaurants = updatedRestaurants)
        val updatedPlans = routePlans.value.toMutableList()
        val planIndex = updatedPlans.indexOfFirst { it.name == plan.name }
        if (planIndex != -1) {
            updatedPlans[planIndex] = updatedPlan
            routePlans.value = updatedPlans
        }
    }

    Scaffold(
        bottomBar = {
            BottomAppBar {
                IconButton(onClick = { navController.navigate("search") }) {
                    Icon(Icons.Filled.Search, contentDescription = "Search")
                }
                IconButton(onClick = { navController.navigate("favorites") }) {
                    Icon(Icons.Filled.Favorite, contentDescription = "Favorites")
                }
                IconButton(onClick = { navController.navigate("route") }) {
                    Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Route Plan")
                }
                IconButton(onClick = { (context as? Activity)?.finish() }) {
                    Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Exit")
                }
            }
        }
    ) { innerPadding ->
        NavHost(navController, startDestination = "search", Modifier.padding(innerPadding)) {
            composable("search") { 
                SearchScreen(
                    restaurants = restaurants.value,
                    onFavoriteClick = ::toggleFavorite
                )
            }
            composable("favorites") {
                FavoritesScreen(
                    favoriteRestaurants = favoriteRestaurants,
                    routePlans = routePlans.value,
                    onAddRestaurantToPlan = ::addRestaurantToPlan,
                    onFavoriteClick = ::toggleFavorite
                )
            }
            composable("route") {
                RoutePlanScreen(
                    routePlans = routePlans.value,
                    onRemoveRestaurant = ::removeRestaurantFromPlan,
                    onReorder = ::reorderRestaurantsInPlan
                )
            }
        }
    }
}