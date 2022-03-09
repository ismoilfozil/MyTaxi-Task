package com.example.mytaxi_task.data.remote

import com.example.mytaxi_task.data.model.response.geocode.GeocodeResponse
import com.example.mytaxi_task.data.model.response.routing.RoutingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("directions/json")
    suspend fun locationsRouting(
        @Query("origin") origin:String,
        @Query("destination") destination:String,
        @Query("sensor") sensor:Boolean,
        @Query("units") units:String,
        @Query("mode") mode:String,
        @Query("key") key:String,
    ):RoutingResponse?

    @GET("geocode/json")
    suspend fun gecodeCoordinate(
        @Query("latlng") latlng:String,
        @Query("key") key:String,
    ):GeocodeResponse?

}