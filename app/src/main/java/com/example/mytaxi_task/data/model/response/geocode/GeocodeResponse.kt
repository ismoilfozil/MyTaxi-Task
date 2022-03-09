package com.example.mytaxi_task.data.model.response.geocode

data class GeocodeResponse(
    val plus_code: PlusCode,
    val results: List<Result>,
    val status: String
)