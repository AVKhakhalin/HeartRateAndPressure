package com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.databinding.ActivityMainBinding
import com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.utils.*
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.java.KoinJavaComponent.getKoin
import java.util.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.databinding.DialogfragmendAddNewDataBinding
import com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.model.AppState
import com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.model.HealthData
import com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.view.adapter.MainActivityRecyclerAdapter
import java.text.SimpleDateFormat


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

    override fun onDestroy() {
        // Удаление скоупа для данной активити
        mainActivityScope.close()
        super.onDestroy()
    }

    // Функция - слушатель нажатий по FAB
    @SuppressLint("ResourceType")
    private fun onClickFab() {
        binding.fabMain.setOnClickListener {
            val builder = AlertDialog.Builder(this@MainActivity)
            val inflater = layoutInflater
            val dialogView = DialogfragmendAddNewDataBinding.inflate(inflater)
            builder.setView(dialogView.root)
            val dialog = builder.create()
            setAlertDialogButtonListener(dialog, dialogView)
            dialog.show()
        }
    }

    // Настройка диалогового фрагмента
    private fun setAlertDialogButtonListener(dialog: AlertDialog,
                                             dialogView: DialogfragmendAddNewDataBinding) {
        val cureDate: Date = Date()
        dialogView.addNewDataDateField.setText(
            SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(cureDate))
        dialogView.addNewDataTimeField.setText(
            SimpleDateFormat(TIME_FORMAT, Locale.getDefault()).format(cureDate))
        // Действие, в случае нажатия на кнопку "Сохранить"
        dialogView.addNewDataSaveButton.setOnClickListener {
            val date: String = dialogView.addNewDataDateField.text.toString()
            val time: String = dialogView.addNewDataTimeField.text.toString()
            val pressureTop: String = dialogView.addNewDataPressureTopField.text.toString()
            val pressureBottom: String = dialogView.addNewDataPressureBottomField.text.toString()
            val heartRate: String = dialogView.addNewDataHeartRateField.text.toString()
            lateinit var healthData: HealthData
            if (dialogView.addNewDataTimeField.text.toString().getHour().isAmTime()) {
                healthData = HealthData(
                    simpleDate = "${date.getDay()}/${date.getMonth()}/${date.getYear()}",
                    daysTime = "${time.getHour()}:${time.getMinute()}",
                    daysPressureTop = if (pressureTop.isNotEmpty()) pressureTop.toInt()
                                      else EMPTY_INT_CODE,
                    daysPressureBottom = if (pressureBottom.isNotEmpty()) pressureBottom.toInt()
                                         else EMPTY_INT_CODE,
                    daysHeartRate = if (heartRate.isNotEmpty()) heartRate.toInt()
                                    else EMPTY_INT_CODE)
            } else {
                healthData = HealthData(
                    simpleDate = "${date.getDay()}/${date.getMonth()}/${date.getYear()}",
                    nightsTime = "${time.getHour()}:${time.getMinute()}",
                    nightsPressureTop = if (pressureTop.isNotEmpty()) pressureTop.toInt()
                                      else EMPTY_INT_CODE,
                    nightsPressureBottom = if (pressureBottom.isNotEmpty()) pressureBottom.toInt()
                                         else EMPTY_INT_CODE,
                    nightsHeartRate = if (heartRate.isNotEmpty()) heartRate.toInt()
                                    else EMPTY_INT_CODE)
            }
            viewModel.saveData(healthData)
            dialog.dismiss()
        }
        // Действие, в случае нажатия на кнопку "Отмена"
        dialogView.addNewDataCancelButton.setOnClickListener {
            // Закрытие диалогового окна в случае отмены действия
            dialog.dismiss()
        }
    }
}