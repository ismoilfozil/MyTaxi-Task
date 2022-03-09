package com.example.mytaxi_task.ui.trips.info

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mytaxi_task.R
import com.example.mytaxi_task.databinding.FragmentTripsInfoBinding
import com.example.mytaxi_task.utils.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.JointType
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TripsInfoFragment : Fragment(), OnMapReadyCallback {


    private var _binding: FragmentTripsInfoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TripsInfoViewModel by viewModels()

    private lateinit var mMap: GoogleMap
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }
    private val args: TripsInfoFragmentArgs by navArgs()
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTripsInfoBinding.inflate(inflater, container, false)
        activity?.setTransparentStatusBar()
        fitsLayoutToSystemWindow()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModelObserve()
        setupUI()
    }

    private fun viewModelObserve() {
        viewModel.locationRoutingLiveData.observe(viewLifecycleOwner){ list ->
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(list[0], 14f))
            drawRoute(list)
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner){
            it.showLogTTT()
        }
    }


    private fun setupUI() {
        initMap()
        setData()
        binding.buttonNav.setOnClickListener {
            navController.popBackStack()
        }
    }



    private fun setData() {
        val data = args.trip
        binding.textPlaceComing.text = data.comingPlaceName
        binding.textPlaceGoing.text = data.goingPlaceName
        binding.imageCarType.setImageResource(data.type.id)
    }

    private fun loadRemoteData(origin: LatLng, destination:LatLng) {
        viewModel.locationRouting(origin, destination)
    }


    private fun initMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val data = args.trip
        loadRemoteData(data.goingPlace.toLatLng, data.comingPlace.toLatLng)
    }


    private fun decodeToLatLng(list: List<List<Double>>) {
        val latLngList = ArrayList<LatLng>()
        for (i in list.indices) {
            if (i != list.lastIndex) {
                val lng: Double = list[i][0]
                val lat: Double = list[i][1]
                latLngList.add(LatLng(lat, lng))
            }
        }
        mMap.clear()
        drawRoute(latLngList)
    }

    private fun drawRoute(list: ArrayList<LatLng>) {
        mMap.addPolyline(PolylineOptions().addAll(list).width(15F).color(Color.parseColor("#3F7BEB")).geodesic(true).jointType(JointType.ROUND))

        val origin = list[0]
        val destination = list[list.lastIndex]

        mMap.addMarker(MarkerOptions().position(origin).icon(requireContext().bitmapDescriptorFromVector(R.drawable.ic_coordinate_blue)))
        mMap.addMarker(MarkerOptions().position(destination).icon(requireContext().bitmapDescriptorFromVector(R.drawable.ic_coordinate)))
    }



    private fun fitsLayoutToSystemWindow() {
        val rootLayoutParams = binding.contentLayout.layoutParams as CoordinatorLayout.LayoutParams
        rootLayoutParams.topMargin = (activity?.getStatusBarHeight()?.plus(22.toPx))?.div(1.toPx) ?: 22.toPx

        bottomSheetBehavior = BottomSheetBehavior.from(binding.infoLayout)
        val peekHeight = resources.displayMetrics.heightPixels * 2 / 3
        bottomSheetBehavior.setPeekHeight(peekHeight, true)

        val infoLayout = binding.buttonDelete.layoutParams as ConstraintLayout.LayoutParams
        infoLayout.bottomMargin = (activity?.getNavigationBarHeight()?.plus(20.toPx)) ?: 20.toPx
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}