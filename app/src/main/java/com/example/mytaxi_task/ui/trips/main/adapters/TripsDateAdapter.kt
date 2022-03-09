package com.example.mytaxi_task.ui.trips.main.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytaxi_task.data.model.Trip
import com.example.mytaxi_task.databinding.ItemLayoutTripsDateBinding
import java.util.*
import kotlin.collections.ArrayList

class TripsDateAdapter(
    private var trips: List<Trip>,
    private var dates: List<String>,
    private val context: Context?,
) : RecyclerView.Adapter<TripsDateAdapter.ViewHolder>() {

    private var innerItemClickListener: ((Trip) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemLayoutTripsDateBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(position)

    override fun getItemCount(): Int = dates.size


    fun swapData(trips: List<Trip>, dates: List<String>) {
        this.trips = trips
        this.dates = dates
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemLayoutTripsDateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val date = dates[dates.lastIndex - position]
            binding.textDate.text = date
            val dailyTrips = getTripsInThisDay(date)

            val adapter = TripsListAdapter(dailyTrips)
            binding.list.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            binding.list.adapter = adapter

            adapter.setOnItemClickListener { trip ->
                innerItemClickListener?.invoke(trip)
            }


        }
    }

    fun setOnItemClickListener(block:(Trip) -> Unit){
        innerItemClickListener = block
    }


    private fun getTripsInThisDay(date: String): List<Trip> {
        val list = ArrayList<Trip>()
        trips.forEach { trip ->
            if (trip.date.toDateFormat() == date) {
                list.add(trip)
            }
        }
        return list
    }

}


fun Long.toDateFormat(): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val month = calendar.get(Calendar.MONTH).toMonth
    val week = calendar.get(Calendar.DAY_OF_WEEK).toWeek

    return "$day $month, $week"
}


val Int.toMonth
    get() = when (this) {
        0 -> "Января"
        1 -> "Февраля"
        2 -> "Марта"
        3 -> "Апреля"
        4 -> "Мая"
        5 -> "Июня"
        6 -> "Июля"
        7 -> "Августа"
        8 -> "Сентября"
        9 -> "Октября"
        10 -> "Ноября"
        11 -> "Декабря"
        else -> ""
    }


val Int.toWeek
    get() = when (this) {
        1 -> "Понедельник"
        2 -> "Вторник"
        3 -> "Среда"
        4 -> "Четвергов"
        5 -> "Пятница"
        6 -> "Суббота"
        7 -> "Воскресенье"
        else -> ""
    }