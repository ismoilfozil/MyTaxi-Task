package com.example.mytaxi_task.ui.main

import android.Manifest
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.mytaxi_task.MainActivity
import com.example.mytaxi_task.R
import com.example.mytaxi_task.databinding.FragmentMainBinding
import com.example.mytaxi_task.utils.*
import com.example.mytaxi_task.utils.Constants.DEFAULT_ZOOM
import com.example.mytaxi_task.utils.helpers.checkPermission
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*

@AndroidEntryPoint
class MainFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var mMap: GoogleMap
    private var lastKnownLocation: Location? = null

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        activity?.setTransparentStatusBar()
        fitsLayoutToSystemWindow()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModelObserve()
        setupUI()
    }


    private fun initMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    private fun setupUI() {
        initMap()

        binding.buttonNav.setOnClickListener {
            (activity as MainActivity).openDrawer()
        }

        binding.moveMyLocation.setOnClickListener {
            moveMyLocation()
        }
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isCompassEnabled = false
        moveMyLocation()

        var latLngVal: LatLng
        mMap.setOnCameraIdleListener {
            latLngVal = mMap.cameraPosition.target
            askLocation(latLngVal)
        }
    }

    private fun askLocation(latLng: LatLng) {
        viewModel.geocode(latLng)
    }

    private fun viewModelObserve() {
        viewModel.geocodeCoordinateLiveData.observe(viewLifecycleOwner){
            binding.originName.setText(it)
        }
    }

    private fun moveMyLocation() {
        // Construct a FusedLocationProviderClient.
        val fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
        val defaultLocation = LatLng(41.31112706285054, 69.27952868106291)

        try {
            activity?.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) {
                val locationResult = fusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        lastKnownLocation = task.result
                        if (lastKnownLocation != null) {
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(
                                lastKnownLocation!!.latitude,
                                lastKnownLocation!!.longitude), DEFAULT_ZOOM.toFloat()))
                        }
                    } else {
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation,
                            DEFAULT_ZOOM.toFloat()))
                        mMap.uiSettings.isMyLocationButtonEnabled = false
                    }
                }
            }
        } catch (e: Exception) {

        }
    }


    private fun fitsLayoutToSystemWindow() {
        val layoutParams = binding.contentLayout.layoutParams as ConstraintLayout.LayoutParams
        layoutParams.topMargin =
            (activity?.getStatusBarHeight()?.plus(16.toPx))?.div(1.toPx) ?: 16.toPx

        val location = binding.containerLocation.layoutParams as LinearLayout.LayoutParams
        location.bottomMargin = (activity?.getNavigationBarHeight()?.plus(16.toPx)) ?: 16.toPx

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}