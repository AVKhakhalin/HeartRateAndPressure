package com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.databinding.ActivityMainBinding

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

//        // Initialise FirebaseFirestore
//        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
//
//        //region WRITE DATA
//        // Create a new user with a first and last name
//        val userOne = hashMapOf(
//            "first" to "Ada",
//            "last" to "Lovelace",
//            "born" to 1815
//        )
//
//        // Add a new document with a generated ID
//        db.collection("users")
//            .add(userOne)
//            .addOnSuccessListener { documentReference ->
//                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
//            }
//            .addOnFailureListener { e ->
//                Log.w(TAG, "Error adding document", e)
//            }
//
//
//        // Create a new user with a first, middle, and last name
//        val userTwo = hashMapOf(
//            "first" to "Alan",
//            "middle" to "Mathison",
//            "last" to "Turing",
//            "born" to 1912
//        )
//
//        // Add a new document with a generated ID
//        db.collection("users")
//            .add(userTwo)
//            .addOnSuccessListener { documentReference ->
//                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
//            }
//            .addOnFailureListener { e ->
//                Log.w(TAG, "Error adding document", e)
//            }
//        //endregion
//
//
//        //region READ DATA
//        db.collection("users")
//            .get()
//            .addOnSuccessListener { result ->
//                for (document in result) {
//                    Log.d(TAG, "${document.id} => ${document.data}")
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.w(TAG, "Error getting documents.", exception)
//            }
//        //endregion
//
//        setContentView(binding.root)
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