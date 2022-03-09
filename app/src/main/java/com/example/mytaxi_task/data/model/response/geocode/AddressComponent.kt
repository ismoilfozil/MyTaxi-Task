package com.example.mytaxi_task.data.model.response.geocode

data class AddressComponent(
    val long_name: String,
    val short_name: String,
    val types: List<String>
)