package com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.model

import kotlinx.serialization.Serializable

@Serializable
data class HealthData(
    val simpleData: String,
    val nightTime: String,
    val nightsPressureTop: Int,
    val nightsPressureBottom: Int,
    val nightsHeartRate: Int,
    val daysTime: String,
    val daysPressureTop: Int,
    val daysPressureBottom: Int,
    val daysHeartRate: Int,
)