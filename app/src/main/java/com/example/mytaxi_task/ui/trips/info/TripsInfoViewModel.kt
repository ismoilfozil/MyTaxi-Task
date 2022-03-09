package com.example.mytaxi_task.ui.trips.info

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytaxi_task.data.repositories.MapRepository
import com.example.mytaxi_task.utils.Constants
import com.example.mytaxi_task.utils.showLogTTT
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TripsInfoViewModel @Inject constructor(
    private val mapRepository: MapRepository,
) : ViewModel() {

    private val _locationRoutingLiveData = MutableLiveData<ArrayList<LatLng>>()
    val locationRoutingLiveData: LiveData<ArrayList<LatLng>> = _locationRoutingLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = _errorLiveData

    fun locationRouting(origin: LatLng, destination: LatLng) = viewModelScope.launch {
        try {
            mapRepository.locationRouting(
                "${origin.latitude},${origin.longitude}",
                "${destination.latitude},${destination.longitude}",
                Constants.SENSOR,
                Constants.UNITS,
                Constants.MODE,
                Constants.API_KEY
            ).let {
                if (it != null && it.status == "OK") {
                    val path = ArrayList<LatLng>()
                    for (i in 0 until it.routes[0].legs[0].steps.size) {
                        val start_latLng = LatLng(it.routes[0].legs[0].steps[i].start_location.lat, it.routes[0].legs[0].steps[i].start_location.lng)
                        path.add(start_latLng)
                        val end_latLng = LatLng(it.routes[0].legs[0].steps[i].end_location.lat, it.routes[0].legs[0].steps[i].end_location.lng)
                        path.add(end_latLng)
                    }
                    _locationRoutingLiveData.postValue(path)
                }
            }
        } catch (e: Exception) {
            "exception = ${e.message}".showLogTTT()
        }
    }

}