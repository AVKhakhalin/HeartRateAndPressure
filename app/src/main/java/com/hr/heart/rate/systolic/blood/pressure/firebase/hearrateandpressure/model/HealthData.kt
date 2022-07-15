package com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.model

import com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.utils.EMPTY_INT_CODE
import com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.utils.EMPTY_STRING_CODE
import kotlinx.serialization.Serializable

@Serializable
data class HealthData(
    val simpleDate: String = EMPTY_STRING_CODE,
    val nightsTime: String = EMPTY_STRING_CODE,
    val nightsPressureTop: Int = EMPTY_INT_CODE,
    val nightsPressureBottom: Int = EMPTY_INT_CODE,
    val nightsHeartRate: Int = EMPTY_INT_CODE,
    val daysTime: String = EMPTY_STRING_CODE,
    val daysPressureTop: Int = EMPTY_INT_CODE,
    val daysPressureBottom: Int = EMPTY_INT_CODE,
    val daysHeartRate: Int = EMPTY_INT_CODE
)