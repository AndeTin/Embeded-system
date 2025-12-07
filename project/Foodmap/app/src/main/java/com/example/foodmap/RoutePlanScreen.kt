package com.example.foodmap

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DragHandle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.layout.onSizeChanged
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
            routePlans.forEach { plan ->
                item(key = plan.name) {
                    Text(text = plan.name, modifier = Modifier.padding(vertical = 8.dp))
                }
                itemsIndexed(plan.restaurants, key = { _, restaurant -> restaurant.name }) { restaurantIndex, restaurant ->
                    var isBeingDragged by remember { mutableStateOf(false) }
                    var dragAccumulator by remember { mutableStateOf(0f) }
                    var itemHeightPx by remember { mutableStateOf(0f) }

                    DraggableRestaurantItem(
                        restaurant = restaurant,
                        isBeingDragged = isBeingDragged,
                        onRemoveClick = { onRemoveRestaurant(restaurant, plan) },
                        modifier = Modifier.onSizeChanged { itemHeightPx = it.height.toFloat() },
                        onDrag = { dragAmountY ->
                            dragAccumulator += dragAmountY
                            if (itemHeightPx > 0) {
                                val threshold = itemHeightPx / 2
                                if (dragAccumulator > threshold && restaurantIndex < plan.restaurants.size - 1) {
                                    onReorder(plan, restaurantIndex, restaurantIndex + 1)
                                    dragAccumulator = 0f
                                } else if (dragAccumulator < -threshold && restaurantIndex > 0) {
                                    onReorder(plan, restaurantIndex, restaurantIndex - 1)
                                    dragAccumulator = 0f
                                }
                            }
                        },
                        onDragStateChange = { isDragging ->
                            isBeingDragged = isDragging
                            if (!isDragging) {
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
    onDragStateChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = if (isBeingDragged) 8.dp else 2.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier
                .weight(1f)
                .padding(vertical = 8.dp)) {
                Text(text = restaurant.name)
                Text(text = restaurant.address)
                Text(text = restaurant.cuisine)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onRemoveClick) {
                    Icon(Icons.Default.Delete, contentDescription = "Remove")
                }
                Icon(
                    imageVector = Icons.Default.DragHandle,
                    contentDescription = "Drag to reorder",
                    modifier = Modifier
                        .pointerInput(Unit) {
                            detectDragGestures(
                                onDragStart = { onDragStateChange(true) },
                                onDragEnd = { onDragStateChange(false) },
                                onDragCancel = { onDragStateChange(false) },
                                onDrag = { change, dragAmount ->
                                    change.consume()
                                    onDrag(dragAmount.y)
                                }
                            )
                        }
                        .padding(8.dp)
                )
            }
        }
    }
}