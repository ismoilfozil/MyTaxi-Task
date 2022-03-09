package com.example.mytaxi_task.data.model

import java.io.Serializable

data class Trip(
    val date: Long,
    val price: String,
    val comingPlaceName: String,
    val goingPlaceName: String,
    val comingPlace: Coordinate,
    val goingPlace: Coordinate,
    val type: CarType,
) : Serializable