package com.example.mytaxi_task.data.model.response.routing

data class GeocodedWaypoint(
    val geocoder_status: String,
    val place_id: String,
    val types: List<String>
)