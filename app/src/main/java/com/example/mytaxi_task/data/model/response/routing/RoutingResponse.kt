package com.example.mytaxi_task.data.model.response.routing

data class RoutingResponse(
    val geocoded_waypoints: List<GeocodedWaypoint>,
    val routes: List<Route>,
    val status: String
)