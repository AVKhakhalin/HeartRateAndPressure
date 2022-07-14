package com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.view

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.databinding.ActivityMainBinding
import com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.model.HealthData
import com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.utils.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity: AppCompatActivity() {
    /** Исходные данные */ //region
    // Binding
    private lateinit var binding: ActivityMainBinding
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Подключение Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        // Инициализация FAB
        onClickFab()

        // Initialise FirebaseFirestore
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()

        //region WRITE DATA
        var healthData: HealthData = HealthData(
            "23:54", 137, 71, 59,
            "8:01", 126, 67, 49
        )

        var data = hashMapOf(
            "data" to Date(),
            "healthData" to healthData
        )

        // Add a new document with a generated ID
        db.collection("HeartRateAndPressure")
            .add(data)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
        //endregion


        //region READ DATA
        db.collection("HeartRateAndPressure")
            .orderBy(DATA_KEY, Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "Получено: ${document.id} => ${document.data}")
                    Log.d("mylogs", "${document.data["data"]}")

                    Log.d("mylogs", SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).
                        format(document.data.convertFirebaseDateStringToCalendar().time))
                    document.data.convertFirebaseHealthDataToHealthData()?.let {
                        Log.d("mylogs", it.daysTime)
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
        //endregion

        setContentView(binding.root)
    }

    // Функция - слушатель нажатий по FAB
    @SuppressLint("ResourceType")
    private fun onClickFab() {
        with(binding) {
            fabMain.setOnClickListener {
                Toast.makeText(this@MainActivity,
                    "Нажали на кнопку FAB", Toast.LENGTH_SHORT).show()
            }
        }
    }
}