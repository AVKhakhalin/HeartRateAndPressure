package com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.di

import com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.repository.FirestoreRepo
import com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.repository.FirestoreRepoImpl
import com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.utils.MAIN_ACTIVITY_SCOPE
import com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.utils.resources.ResourcesProvider
import com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.utils.resources.ResourcesProviderImpl
import com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.view.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val application = module {

     // Вспомогательные классы:
    // Получение доступа к ресурсам
    single<ResourcesProvider> { ResourcesProviderImpl(androidContext()) }
}

val screens = module {
    // Scope для MainActivity
    scope(named(MAIN_ACTIVITY_SCOPE)) {
        viewModel {
            MainViewModel()
        }
    }
}

val repository = module {
    single<FirestoreRepo> { FirestoreRepoImpl() }
}
