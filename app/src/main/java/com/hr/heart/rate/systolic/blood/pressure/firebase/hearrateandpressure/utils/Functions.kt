package com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.utils

import com.google.gson.Gson
import com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.model.HealthData
import kotlin.math.abs

fun Map<String, Any>.convertFirebaseHealthDataToHealthData(): HealthData? {
    val stringForJSON: String = this[HEALTHDATA_KEY].toString().
        replace("{", "{\"").
        replace("=", "\"=\"").
        replace(", ", "\", \"").
        replace("}", "\"}")
    return Gson().fromJson(stringForJSON, HealthData::class.java)
}

// Определение времени Am (true) или Pm (false)
fun Int.isAmTime(): Boolean {
    return (this >= 0) && (this < 12)
}

// Получение часа из строки пользователя
fun String.getHour(): Int {
    val time: Array<String> =
        this.split(".", ":", "/", "|", "\\", " ").toTypedArray()
    var hours: Int = if (time[HOUR_INDEX].isNotEmpty()) abs(time[HOUR_INDEX].toInt()) else 0
    val minutes: Int = if (time[MINUTE_INDEX].isNotEmpty()) abs(time[MINUTE_INDEX].toInt()) else 0
    if (minutes > 59) {
        hours++
    }
    if (hours > 23) hours %= 24
    return hours
}
// Получение минут из строки пользователя
fun String.getMinute(): Int {
    val time: Array<String> =
        this.split(".", ":", "/", "|", "\\", " ").toTypedArray()
    var minutes: Int = if (time[MINUTE_INDEX].isNotEmpty()) abs(time[MINUTE_INDEX].toInt()) else 0
    if (minutes > 59) {
        minutes -= MINUTES_IN_HOUR
    }
    return minutes
}
// Получение дня из строки пользователя
fun String.getDay(): Int {
    val time: Array<String> =
        this.split(".", ":", "/", "|", "\\", " ").toTypedArray()
    return if (time[DAY_INDEX].isNotEmpty()) time[DAY_INDEX].toInt() else 0
}
// Получение месяца из строки пользователя
fun String.getMonth(): Int {
    val time: Array<String> =
        this.split(".", ":", "/", "|", "\\", " ").toTypedArray()
    return if (time[MONTH_INDEX].isNotEmpty()) time[MONTH_INDEX].toInt() else 0
}
// Получение года из строки пользователя
fun String.getYear(): Int {
    val time: Array<String> =
        this.split(".", ":", "/", "|", "\\", " ").toTypedArray()
    return if (time[YEAR_INDEX].isNotEmpty()) time[YEAR_INDEX].toInt() else 0
}