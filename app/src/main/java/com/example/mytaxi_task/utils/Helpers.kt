package com.example.mytaxi_task.utils

import com.example.mytaxi_task.data.model.Coordinate
import com.google.android.gms.maps.model.LatLng

val Coordinate.toLatLng get() = LatLng(this.latitude, this.longitude)