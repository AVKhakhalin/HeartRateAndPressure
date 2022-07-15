package com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.app

import android.app.Application
import com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.di.application
import com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.di.repository
import com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.di.screens
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    /** Исходные данные */ //region
    companion object {
        lateinit var instance: App
    }
    // endregion

    override fun onCreate() {
        super.onCreate()
        instance = this

        // Koin
        startKoin {
            androidContext(applicationContext)
            modules(listOf(application, screens, repository))
        }
    }
}