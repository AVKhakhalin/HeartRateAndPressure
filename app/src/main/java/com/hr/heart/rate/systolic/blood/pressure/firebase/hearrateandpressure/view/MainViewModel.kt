package com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.view

import androidx.lifecycle.LiveData
import com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.base.BaseViewModel
import com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.model.AppState
import com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.model.HealthData
import kotlinx.coroutines.launch

class MainViewModel: BaseViewModel<AppState>() {
    /** Задание переменных */ //region
    // Информация с результатом запроса
    private val liveDataForViewToObserve: LiveData<AppState> = _mutableLiveData
    // Интерактор
    val interactor: MainViewModelInteractor = MainViewModelInteractor()
    //endregion

    override fun getData() {
        _mutableLiveData.value = AppState.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch {
            interactor.getHealthDataList(this@MainViewModel)
        }
    }

    fun setData(healthDataList: List<HealthData>) {
        _mutableLiveData.postValue(AppState.Success(healthDataList))
    }

    override fun saveData(healthData: HealthData) {
        viewModelCoroutineScope.launch {
            interactor.saveHealthData()
        }
    }

    fun subscribe(): LiveData<AppState> {
        return liveDataForViewToObserve
    }

    override fun handleError(error: Throwable) {
    }
}