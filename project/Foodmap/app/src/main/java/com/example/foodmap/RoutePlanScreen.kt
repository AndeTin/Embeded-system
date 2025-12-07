package com.example.foodmap

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
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

                    DraggableRestaurantItem(
                        restaurant = restaurant,
                        isBeingDragged = isBeingDragged,
                        onRemoveClick = { onRemoveRestaurant(restaurant, plan) },
                        modifier = Modifier.animateItemPlacement(),
                        onReorder = { from, to -> onReorder(plan, from, to) },
                        index = restaurantIndex,
                        listSize = plan.restaurants.size,
                        onDragStateChange = { isDragging -> isBeingDragged = isDragging }
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
    onReorder: (from: Int, to: Int) -> Unit,
    index: Int,
    listSize: Int,
    onDragStateChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val elevation by animateDpAsState(if (isBeingDragged) 8.dp else 2.dp, label = "elevation")
    var dragAccumulator by remember { mutableFloatStateOf(0f) }
    var itemHeightPx by remember { mutableStateOf(0) }

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = elevation),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .onSizeChanged { itemHeightPx = it.height }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 8.dp)
            ) {
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
                                onDragEnd = {
                                    onDragStateChange(false)
                                    dragAccumulator = 0f
                                },
                                onDragCancel = {
                                    onDragStateChange(false)
                                    dragAccumulator = 0f
                                },
                                onDrag = { change, dragAmount ->
                                    change.consume()
                                    dragAccumulator += dragAmount.y
                                    if (itemHeightPx > 0) {
                                        val threshold = itemHeightPx * 0.75f // Trigger when dragged 3/4 of the item height
                                        if (dragAccumulator > threshold && index < listSize - 1) {
                                            onReorder(index, index + 1)
                                            dragAccumulator = 0f
                                        } else if (dragAccumulator < -threshold && index > 0) {
                                            onReorder(index, index - 1)
                                            dragAccumulator = 0f
                                        }
                                    }
                                }
                            )
                        }
                        .padding(16.dp)
                )
            }
        }
    }
}