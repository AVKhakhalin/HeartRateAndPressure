package com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.model.HealthData
import com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.utils.COLLECTION_NAME
import com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.utils.DATA_KEY
import com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.utils.HEALTHDATA_KEY
import com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.utils.convertFirebaseHealthDataToHealthData
import com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.view.MainViewModel
import java.util.*

class FirestoreRepoImpl: FirestoreRepo {
    /** Исходные данные */ //region
    // Initialise FirebaseFirestore
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    //endregion

    override suspend fun getDataList(viewModel: MainViewModel) {
        readData(viewModel)
    }

    override suspend fun saveData(healthData: HealthData, viewModel: MainViewModel) {
        //region WRITE NEW DATA
        val data = hashMapOf(
            DATA_KEY to Date(),
            HEALTHDATA_KEY to healthData
        )
        // Add a new document with a generated ID
        db.collection(COLLECTION_NAME)
            .add(data)
            .addOnSuccessListener { documentReference ->
                readData(viewModel)
            }
            .addOnFailureListener { e ->
                Log.w("mylogs", "Error adding document", e)
            }
        //endregion
    }

    suspend fun editData() {
        // Update document by it ID
        val healthData: HealthData = HealthData(
            "25.10.2022",
            "23:54", 137, 71, 59,
            "8:01", 126, 67, 49,
        )
        var data = hashMapOf(
            DATA_KEY to Date(),
            HEALTHDATA_KEY to healthData
        )
        db.collection(COLLECTION_NAME)
            .document("nffzzR7XjuVk11WOqWjG")
            .update(data)
            .addOnSuccessListener { documentReference ->
                Log.d("mylogs", "DocumentSnapshot successefull updated!")
            }
            .addOnFailureListener { e ->
                Log.w("mylogs", "Error adding document", e)
            }

        // Delete document by it ID
        db.collection(COLLECTION_NAME)
            .document("nffzzR7XjuVk11WOqWjG")
            .delete()
            .addOnSuccessListener { documentReference ->
                Log.d("mylogs", "DocumentSnapshot successefull deleted!")
            }
            .addOnFailureListener { e ->
                Log.w("mylogs", "Error adding document", e)
            }
    }

    private fun readData(viewModel: MainViewModel) {
        //region READ DATA
        val healthData: MutableList<HealthData> = mutableListOf()
        db.collection(COLLECTION_NAME)
            .orderBy(DATA_KEY, Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    document.data.convertFirebaseHealthDataToHealthData()?.let {
                        healthData.add(it)
                        viewModel.setData(healthData)
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w("mylogs", "Error getting documents.", exception)
            }
        //endregion

    }
}