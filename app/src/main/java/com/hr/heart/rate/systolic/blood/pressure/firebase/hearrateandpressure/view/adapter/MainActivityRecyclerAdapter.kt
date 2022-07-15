package com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.databinding.ItemListPressureHrBinding
import com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.model.HealthData
import com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.utils.EMPTY_INT_CODE
import com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.utils.resources.ResourcesProvider
import org.koin.java.KoinJavaComponent.getKoin

class MainActivityRecyclerAdapter(
    private val healthDateList: List<HealthData>
): RecyclerView.Adapter<MainActivityRecyclerAdapter.MyViewHolder>() {
    /** Исходные данные */ //region
    // Получение доступа к ресурсам
    private val resourcesProviderImpl: ResourcesProvider = getKoin().get()
    // LayoutInflater
    private val layoutInflater: LayoutInflater =
        LayoutInflater.from(resourcesProviderImpl.getContext())
    /** Исходные данные */ //region
    //endregion

    class MyViewHolder(
        val binding: ItemListPressureHrBinding
    ): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemListPressureHrBinding.inflate(
            layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.dateText.text = healthDateList[position].simpleData
        if ((healthDateList[position].nightsTime.isNotEmpty()) &&
            (healthDateList[position].nightsPressureTop != EMPTY_INT_CODE) &&
            (healthDateList[position].nightsPressureBottom != EMPTY_INT_CODE) &&
            (healthDateList[position].nightsHeartRate != EMPTY_INT_CODE)) {
            holder.binding.nightsTime.text = healthDateList[position].nightsTime
            holder.binding.nightsPressureTop.text = "${healthDateList[position].nightsPressureTop}"
            holder.binding.nightsPressureBottom.text =
                "${healthDateList[position].nightsPressureBottom}"
            holder.binding.nightsHeartRate.text = "${healthDateList[position].nightsHeartRate}"
            holder.binding.nightsResultsContainer.visibility = View.VISIBLE
        } else {
            holder.binding.nightsResultsContainer.visibility = View.GONE
        }
        if ((healthDateList[position].daysTime.isNotEmpty()) &&
            (healthDateList[position].daysPressureTop != EMPTY_INT_CODE) &&
            (healthDateList[position].daysPressureBottom != EMPTY_INT_CODE) &&
            (healthDateList[position].daysHeartRate != EMPTY_INT_CODE)) {
            holder.binding.daysTime.text = healthDateList[position].daysTime
            holder.binding.daysPressureTop.text = "${healthDateList[position].daysPressureTop}"
            holder.binding.daysPressureBottom.text =
                "${healthDateList[position].daysPressureBottom}"
            holder.binding.daysHeartRate.text = "${healthDateList[position].daysHeartRate}"
            holder.binding.daysResultsContainer.visibility = View.VISIBLE
        } else {
            holder.binding.daysResultsContainer.visibility = View.GONE
        }
    }

    override fun getItemCount() = healthDateList.size
}