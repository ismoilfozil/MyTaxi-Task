package com.example.mytaxi_task.ui.trips.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytaxi_task.data.model.CarType
import com.example.mytaxi_task.data.model.Coordinate
import com.example.mytaxi_task.data.model.Resource
import com.example.mytaxi_task.data.model.Trip
import com.example.mytaxi_task.ui.trips.main.adapters.toDateFormat
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class TripsViewModel : ViewModel() {
    private val trips = ArrayList<Trip>()

    private val _getTripsLiveData = MutableLiveData<Resource<List<Trip>>>()
    val getTripsLiveData: LiveData<Resource<List<Trip>>> = _getTripsLiveData


    fun getDates(trips: List<Trip>): List<String> {
        val dates = HashSet<String>()
        trips.sortedBy { trip -> trip.date }

        trips.forEach { trip ->
            dates.add(trip.date.toDateFormat())
        }

        return dates.toList()
    }

    fun getTrips() {
        _getTripsLiveData.value = Resource.Loading()
        viewModelScope.launch {
            loadData()
            delay(3000)
            _getTripsLiveData.postValue(Resource.Success(trips))
        }
    }

    private fun loadData() {
        trips.add(Trip(
            1625589360000,
            "12 900 cум",
            "улица Sharof Rashidov, Ташкент",
            "5a улица Асадуллы Ходжаева",
            Coordinate(41.3098721, 69.2766909),
            Coordinate(41.3090435, 69.2652098),
            CarType.DELIVERY
        ))

        trips.add(Trip(
            1625581440000,
            "14 800 cум",
            "Яшнабадский район, улица Sharof Rashidov, Ташкент",
            "Юнусабадский район, м-в юнусабад-19, ул. дехканабад, 17",
            Coordinate(41.3098721, 69.2766909),
            Coordinate(41.3090435, 69.2652098),
            CarType.ECONOMY
        ))


        trips.add(Trip(

            1625550120000,
            "12 900 cум",
            "улица Sharof Rashidov, Ташкент",
            "5a улица Асадуллы Ходжаева",
            Coordinate(41.3098721, 69.2766909),
            Coordinate(41.3090435, 69.2652098),
            CarType.BUSINESS
        ))

        trips.add(Trip(
            1625463720000,
            "14 800 cум",
            "Яшнабадский район, улица Sharof Rashidov, Ташкент",
            "Юнусабадский район, м-в юнусабад-19, ул. дехканабад, 17",
            Coordinate(41.3098721, 69.2766909),
            Coordinate(41.3090435, 69.2652098),
            CarType.ECONOMY
        ))
    }
}
