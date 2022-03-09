package com.example.mytaxi_task.ui.trips.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mytaxi_task.data.model.Trip
import com.example.mytaxi_task.databinding.ItemLayoutTripsInfoBinding
import java.text.SimpleDateFormat
import java.util.*

class TripsListAdapter(
    private val trips: List<Trip>,
) : RecyclerView.Adapter<TripsListAdapter.ViewHolder>() {


    private var itemClickListener:((Trip) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemLayoutTripsInfoBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(position)

    override fun getItemCount(): Int = trips.size


    inner class ViewHolder(private val binding: ItemLayoutTripsInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val data = trips[position]

            binding.textPlaceGoing.text = data.goingPlaceName
            binding.textPlaceComing.text = data.comingPlaceName
            binding.textTime.text = data.date.toTimeFormat()
            binding.textPrice.text = data.price
            binding.imageCarType.setImageResource(data.type.id)

            binding.root.setOnClickListener {
                itemClickListener?.invoke(data)
            }
        }
    }

    fun setOnItemClickListener(block:(Trip) -> Unit){
        itemClickListener = block
    }

}

fun Long.toTimeFormat(): String {
    val sdf = SimpleDateFormat("HH:mm")
    val resultdate = Date(this)
    return sdf.format(resultdate)
}