package com.example.foodmap

import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@Composable
fun RoutePlanScreen(
    routePlans: List<RoutePlan>,
    onRemoveRestaurant: (Restaurant, RoutePlan) -> Unit,
    onReorder: (RoutePlan, Int, Int) -> Unit
) {
    if (routePlans.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("No route planned yet.")
        }
    } else {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(routePlans.size) { index ->
                val plan = routePlans[index]
                Text(text = plan.name, modifier = Modifier.padding(bottom = 8.dp))
                plan.restaurants.forEachIndexed { restaurantIndex, restaurant ->
                    var isBeingDragged by remember { mutableStateOf(false) }
                    var dragAccumulator by remember { mutableStateOf(0f) }
                    DraggableRestaurantItem(
                        restaurant = restaurant,
                        isBeingDragged = isBeingDragged,
                        onRemoveClick = { onRemoveRestaurant(restaurant, plan) },
                        onDrag = { dragAmountY ->
                            dragAccumulator += dragAmountY
                        },
                        onDragStateChange = { isDragging ->
                            isBeingDragged = isDragging
                            if (!isDragging) { // onDragEnd
                                // A simple heuristic for reordering, assuming item height is roughly 100.dp
                                val itemHeight = 100
                                if (dragAccumulator < -itemHeight / 2 && restaurantIndex > 0) {
                                    onReorder(plan, restaurantIndex, restaurantIndex - 1)
                                } else if (dragAccumulator > itemHeight / 2 && restaurantIndex < plan.restaurants.size - 1) {
                                    onReorder(plan, restaurantIndex, restaurantIndex + 1)
                                }
                                dragAccumulator = 0f
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun DraggableRestaurantItem(
    restaurant: Restaurant,
    isBeingDragged: Boolean,
    onRemoveClick: () -> Unit,
    onDrag: (Float) -> Unit,
    onDragStateChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .pointerInput(Unit) {
                detectDragGesturesAfterLongPress(
                    onDragStart = { onDragStateChange(true) },
                    onDragEnd = { onDragStateChange(false) },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        onDrag(dragAmount.y)
                    }
                )
            }
    ) {
        Row(modifier = Modifier.padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text(text = restaurant.name)
                Text(text = restaurant.address)
                Text(text = restaurant.cuisine)
            }
            IconButton(onClick = onRemoveClick) {
                Icon(Icons.Default.Delete, contentDescription = "Remove")
            }
        }
    }
}
