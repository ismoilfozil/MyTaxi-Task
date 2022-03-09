package com.example.mytaxi_task.data.model.response.geocode

data class Geometry(
    val bounds: Bounds,
    val location: Location,
    val location_type: String,
    val viewport: Viewport
)