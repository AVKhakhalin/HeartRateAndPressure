package com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.utils.resources

import android.content.Context
import androidx.annotation.StringRes

interface ResourcesProvider {

    fun getString(@StringRes id: Int): String
    fun getContext(): Context
}