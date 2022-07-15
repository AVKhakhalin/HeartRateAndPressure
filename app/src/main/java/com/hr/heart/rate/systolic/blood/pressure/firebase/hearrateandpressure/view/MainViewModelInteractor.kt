package com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.view

import com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.model.HealthData
import com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.repository.FirestoreRepo
import org.koin.java.KoinJavaComponent.getKoin

class MainViewModelInteractor {
    /** Задание переменных */ //region
    private val fireStoreRepoImpl: FirestoreRepo = getKoin().get()
    //endregion

    suspend fun getHealthDataList(viewModel: MainViewModel) {
        fireStoreRepoImpl.getDataList(viewModel)
    }

    suspend fun saveHealthData(healthData: HealthData, viewModel: MainViewModel) {
        fireStoreRepoImpl.saveData(healthData, viewModel)
    }
}