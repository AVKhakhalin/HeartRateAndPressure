package com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.model.AppState
import com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.model.HealthData
import kotlinx.coroutines.*

abstract class BaseViewModel<T : AppState>(
    protected open val _mutableLiveData: MutableLiveData<T> = MutableLiveData(),
): ViewModel() {
    /** Задание переменных */ //region
    // Скоуп для вьюмодели
    protected val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Main
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            handleError(throwable)
        })
    //endregion

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }

    protected fun cancelJob() {
        viewModelCoroutineScope.coroutineContext.cancelChildren()
    }

    abstract fun getData()

    /** Методы сохранения данных */
    abstract fun saveData(healthData: HealthData)

    abstract fun handleError(error: Throwable)
}