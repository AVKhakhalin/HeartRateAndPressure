package com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.repository

import com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.model.HealthData
import com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.view.MainViewModel

interface FirestoreRepo {
    suspend fun getDataList(viewModel: MainViewModel)
    suspend fun saveData(healthData: HealthData, viewModel: MainViewModel)
}