package com.example.mytaxi_task.data.repositories

import com.example.mytaxi_task.data.remote.ApiService
import javax.inject.Inject


class MapRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun locationRouting(
        origin: String,
        destination: String,
        sensor: Boolean,
        units: String,
        mode: String,
        key: String,
    ) = apiService.locationsRouting(origin, destination, sensor, units, mode, key)


    suspend fun geocodeCoordinate(latLng:String, key: String) = apiService.gecodeCoordinate(latLng, key)

}