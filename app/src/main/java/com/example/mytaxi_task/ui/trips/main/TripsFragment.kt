package com.example.mytaxi_task.ui.trips.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytaxi_task.R
import com.example.mytaxi_task.data.model.Resource
import com.example.mytaxi_task.data.model.Trip
import com.example.mytaxi_task.databinding.FragmentTripsBinding
import com.example.mytaxi_task.ui.trips.main.adapters.TripsDateAdapter
import com.example.mytaxi_task.utils.fixWindow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class TripsFragment : Fragment() {

    private var _binding: FragmentTripsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TripsViewModel by viewModels()
    private var tripsDateAdapter: TripsDateAdapter? = null

    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTripsBinding.inflate(inflater, container, false)
        activity?.fixWindow()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUI()
        observeViewModel()
        viewModel.getTrips()
    }

    private fun setUI() {
        initRecycler(listOf(), listOf())
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeViewModel() {
        viewModel.getTripsLiveData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.shimmerLayout.startShimmer()
                }
                is Resource.Success -> {
                    val trips = result.data
                    val dates = viewModel.getDates(trips!!)
                    tripsDateAdapter?.swapData(trips, dates)
                    showRecyclerView()
                }
                else -> {}
            }
        }
    }

    private fun initRecycler(trips: List<Trip>, dates: List<String>) {
        tripsDateAdapter = TripsDateAdapter(trips, dates, context)
        binding.list.adapter = tripsDateAdapter
        binding.list.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.list.setHasFixedSize(true)

        tripsDateAdapter?.setOnItemClickListener { trip ->
            navController.navigate(TripsFragmentDirections.actionTripsFragmentToTripsInfoFragment(trip))
        }
    }


    private fun showRecyclerView() {
        binding.shimmerLayout.apply {
            stopShimmer()
            visibility = View.GONE
        }
        binding.list.visibility = View.VISIBLE
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}