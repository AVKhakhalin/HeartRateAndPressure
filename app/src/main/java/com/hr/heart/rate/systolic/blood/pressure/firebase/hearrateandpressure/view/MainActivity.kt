package com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.view

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.databinding.ActivityMainBinding
import com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.model.HealthData
import com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.utils.*
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.java.KoinJavaComponent.getKoin
import java.text.SimpleDateFormat
import java.util.*
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.model.AppState
import com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.view.adapter.MainActivityRecyclerAdapter


class MainActivity: AppCompatActivity() {
    /** Исходные данные */ //region
    // Binding
    private lateinit var binding: ActivityMainBinding
    // ViewModel
    private val mainActivityScope: Scope = getKoin().getOrCreateScope(
        MAIN_ACTIVITY_SCOPE, named(MAIN_ACTIVITY_SCOPE)
    )
    private lateinit var viewModel: MainViewModel
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // !!! Убрать перед отправкой
//        addDataToFirebase()

        // Подключение Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        // Инициализация ViewModel
        initViewModel()
        // Инициализация FAB
        onClickFab()

        setContentView(binding.root)
    }

    // Инициализация ViewModel
    private fun initViewModel() {
        // Создание Scope для MainActivity
        val viewModel: MainViewModel by mainActivityScope.inject()
        this.viewModel = viewModel
        // Подписка на ViewModel
        this.viewModel.subscribe().observe(this) { renderData(it) }
        // Получение данных
        viewModel.getData()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                // Изменение внешнего вида
                binding.heartRatePressureDataList.visibility = View.VISIBLE
                binding.progressbar.visibility = View.INVISIBLE
                // Установка списка актёров
                appState.healthDataList?.let { healthDataList ->
                    val recyclerView: RecyclerView = binding.heartRatePressureDataList
                    recyclerView.layoutManager = LinearLayoutManager(
                        this, LinearLayoutManager.VERTICAL, false)
                    recyclerView.adapter = MainActivityRecyclerAdapter(healthDataList)
                }
            }
            is AppState.Loading -> {
                // Изменение внешнего вида
                binding.heartRatePressureDataList.visibility = View.INVISIBLE
                binding.progressbar.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                Toast.makeText(this@MainActivity,
                    appState.error.message, Toast.LENGTH_SHORT).show()
            }
        }
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

    override fun onDestroy() {
        // Удаление скоупа для данной активити
        mainActivityScope.close()
        super.onDestroy()
    }
}