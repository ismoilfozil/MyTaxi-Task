package com.example.mytaxi_task.ui.main

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
class MainViewModel @Inject constructor(private val mapRepository: MapRepository) : ViewModel() {

    private val _geocodeCoordinateLiveData = MutableLiveData<String>()
    val geocodeCoordinateLiveData: LiveData<String> = _geocodeCoordinateLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData:LiveData<String> = _errorLiveData


    fun geocode(latLng: LatLng) = viewModelScope.launch{
        try {
            mapRepository.geocodeCoordinate("${latLng.latitude},${latLng.longitude}", Constants.API_KEY).let {
                if (it != null && it.status == "OK"){
                    "$it".showLogTTT()
                    val name = it.results[0].address_components[0].long_name + " , "+ it.results[0].address_components[1].long_name + " , " + it.results[0].address_components[2].long_name
                    _geocodeCoordinateLiveData.postValue(name)
                }
            }
        }catch (e:Exception){

        }
    }

}