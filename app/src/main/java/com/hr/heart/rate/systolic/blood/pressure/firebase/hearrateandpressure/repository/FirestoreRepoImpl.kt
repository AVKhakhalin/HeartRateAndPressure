package com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.model.HealthData
import com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.model.KeysAndSimpleDate
import com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.utils.*
import com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.view.MainViewModel
import org.koin.java.KoinJavaComponent.getKoin
import java.util.*

class FirestoreRepoImpl: FirestoreRepo {
    /** Исходные данные */ //region
    // Initialise FirebaseFirestore
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    // Settings
    val settings: Settings = getKoin().get()
    //endregion

    override suspend fun getDataList(viewModel: MainViewModel) {
        readData(viewModel)
    }

    override suspend fun saveData(healthData: HealthData, viewModel: MainViewModel) {
        //region WRITE NEW DATA
        // Установка корректной даты для сохранения данных данных
        // на основе вводимой пользователем информации
        val newData: Calendar = Calendar.getInstance()
        newData.set(healthData.simpleDate.getYear(), healthData.simpleDate.getMonth() - 1,
            healthData.simpleDate.getDay(),
            if (healthData.nightsTime.isNotEmpty()) healthData.nightsTime.getHour() else
                healthData.daysTime.getHour(),
            if (healthData.nightsTime.isNotEmpty()) healthData.nightsTime.getMinute() else
                healthData.daysTime.getMinute())
        val currentData: Date = newData.time
        // Создание данных для записи в Firebase
        val data = hashMapOf(
            DATA_KEY to currentData,
            HEALTHDATA_KEY to healthData
        )
        // Поиск существующих записей с такой же датой
        var targetDocumentId: String = ""
        settings.documentsKeys.forEach {
            if (it.simpleDate == healthData.simpleDate)
                targetDocumentId = it.key
        }
        if (targetDocumentId.isEmpty()) {
            // Add a new document with a generated ID
            db.collection(COLLECTION_NAME)
                .add(data)
                .addOnSuccessListener { documentReference ->
                    readData(viewModel)
                }
                .addOnFailureListener { e ->
                    Log.w("mylogs", "Error adding document", e)
                }
        } else {
            // Update document by it ID
            db.collection(COLLECTION_NAME)
                .document(targetDocumentId)
                .get()
                .addOnSuccessListener { result ->
                    settings.documentsKeys.clear()
                    result.data?.let{ resultData ->
                        resultData.convertFirebaseHealthDataToHealthData()?.let {
                            val completeHealthData: HealthData = HealthData(
                                simpleDate = healthData.simpleDate,
                                nightsTime = healthData.nightsTime.ifEmpty { it.nightsTime },
                                nightsPressureTop = if (healthData.nightsPressureTop !=
                                                    EMPTY_INT_CODE) healthData.nightsPressureTop
                                                    else it.nightsPressureTop,
                                nightsPressureBottom = if (healthData.nightsPressureBottom !=
                                                     EMPTY_INT_CODE) healthData.nightsPressureBottom
                                                     else it.nightsPressureBottom,
                                nightsHeartRate = if (healthData.nightsHeartRate !=
                                                  EMPTY_INT_CODE) healthData.nightsHeartRate
                                                  else it.nightsHeartRate,
                                daysTime = healthData.daysTime.ifEmpty { it.daysTime },
                                daysPressureTop = if (healthData.daysPressureTop !=
                                                  EMPTY_INT_CODE) healthData.daysPressureTop
                                                  else it.daysPressureTop,
                                daysPressureBottom = if (healthData.daysPressureBottom !=
                                                    EMPTY_INT_CODE) healthData.daysPressureBottom
                                                    else it.daysPressureBottom,
                                daysHeartRate = if (healthData.daysHeartRate !=
                                                EMPTY_INT_CODE) healthData.daysHeartRate
                                                else it.daysHeartRate
                            )
                            // Создание дополненных данных для записи в Firebase
                            val data = hashMapOf(
                                DATA_KEY to currentData,
                                HEALTHDATA_KEY to completeHealthData
                            )
                            // Обновление данных в Firebase
                            db.collection(COLLECTION_NAME)
                                .document(targetDocumentId)
                                .update(data)
                                .addOnSuccessListener { documentReference ->
                                    readData(viewModel)
                                }
                                .addOnFailureListener { e ->
                                    Log.w("mylogs", "Error adding document", e)
                                }
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("mylogs", "Error getting documents.", exception)
                }
        }
        //endregion
    }

    private fun readData(viewModel: MainViewModel) {
        //region READ DATA
        val healthData: MutableList<HealthData> = mutableListOf()
        db.collection(COLLECTION_NAME)
            .orderBy(DATA_KEY, Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                settings.documentsKeys.clear()
                for (document in result) {
                    document.data.convertFirebaseHealthDataToHealthData()?.let {
                        healthData.add(it)
                        // Получение ключей и дат
                        val keysAndSimpleDate: KeysAndSimpleDate =
                            KeysAndSimpleDate(document.id, it.simpleDate)
                        settings.documentsKeys.add(keysAndSimpleDate)
                    }
                }
                viewModel.setData(healthData)
            }
            .addOnFailureListener { exception ->
                Log.w("mylogs", "Error getting documents.", exception)
            }
        //endregion
    }
}