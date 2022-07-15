package com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.model

sealed class AppState {
    data class Success(val healthDataList: List<HealthData>?): AppState()
    data class Error(val error: Throwable): AppState()
    data class Loading(val progress: Int?): AppState()
}