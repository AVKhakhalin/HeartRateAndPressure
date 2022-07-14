package com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.utils

import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.model.HealthData
import java.text.SimpleDateFormat
import java.util.*

fun Map<String, Any>.convertFirebaseDateStringToCalendar(): Calendar {
    val calendar: Calendar = Calendar.getInstance()
    var secondsString: String  = this[DATA_KEY].toString()
    val zptIndex: Int = secondsString.indexOf(",")
    secondsString = secondsString.substring(secondsString.indexOf("seconds=") + 8, zptIndex)
    var millisecondsString: String  = this[DATA_KEY].toString().replace(")", "")
    millisecondsString = millisecondsString.substring(
        millisecondsString.indexOf("nanoseconds=") + 12)
    millisecondsString = millisecondsString.substring(0, MILLISECONDS_NUMBERS - secondsString.length)
    calendar.timeInMillis = "$secondsString$millisecondsString".toLong()
    return calendar
}

fun Map<String, Any>.convertFirebaseHealthDataToHealthData(): HealthData? {
    val stringForJSON: String = this[HEALTHDATA_KEY].toString().
        replace("{", "{\"").
        replace("=", "\"=\"").
        replace(", ", "\", \"").
        replace("}", "\"}")
    return Gson().fromJson(stringForJSON, HealthData::class.java)
}

fun getDate(milliSeconds: Long, dateFormat: String): String {
    val formatter: SimpleDateFormat = SimpleDateFormat(dateFormat)
    val calendar: Calendar = Calendar.getInstance()
    calendar.timeInMillis = milliSeconds
    return formatter.format(calendar.time)
}