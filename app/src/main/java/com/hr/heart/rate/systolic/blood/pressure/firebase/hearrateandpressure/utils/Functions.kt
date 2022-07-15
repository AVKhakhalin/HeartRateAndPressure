package com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.utils

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
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

fun addDataToFirebase() {
    // Initialise FirebaseFirestore
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    //region WRITE NEW DATA
    var healthData: HealthData = HealthData(
        "23.10.2022",
        "22:06", 150, 83, 55,
        "7:51", 129, 79, 53
    )

    var data = hashMapOf(
        "data" to Date(),
        "healthData" to healthData
    )

    // Add a new document with a generated ID
    db.collection(COLLECTION_NAME)
        .add(data)
        .addOnSuccessListener { documentReference ->
            Log.d("mylogs", "DocumentSnapshot added with ID: ${documentReference.id}")
        }
        .addOnFailureListener { e ->
            Log.w("mylogs", "Error adding document", e)
        }
    //endregion

    //region WRITE NEW DATA
    healthData = HealthData(
        "24.10.2022",
        "22:07", 141, 64, 63,
        "6:39", 127, 73, 58
    )

    data = hashMapOf(
        "data" to Date(),
        "healthData" to healthData
    )

    // Add a new document with a generated ID
    db.collection(COLLECTION_NAME)
        .add(data)
        .addOnSuccessListener { documentReference ->
            Log.d("mylogs", "DocumentSnapshot added with ID: ${documentReference.id}")
        }
        .addOnFailureListener { e ->
            Log.w("mylogs", "Error adding document", e)
        }
    //endregion

    //region WRITE NEW DATA
    healthData = HealthData(
        "25.10.2022",
        "23:54", 137, 71, 59,
        "8:01", 126, 67, 49
    )

    data = hashMapOf(
        "data" to Date(),
        "healthData" to healthData
    )

    // Add a new document with a generated ID
    db.collection(COLLECTION_NAME)
        .add(data)
        .addOnSuccessListener { documentReference ->
            Log.d("mylogs", "DocumentSnapshot added with ID: ${documentReference.id}")
        }
        .addOnFailureListener { e ->
            Log.w("mylogs", "Error adding document", e)
        }
    //endregion
}