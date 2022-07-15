package com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.repository

import com.hr.heart.rate.systolic.blood.pressure.firebase.hearrateandpressure.model.KeysAndSimpleDate

class Settings {
    /** Исходные данные */ //region
    // Ключи от текущих документов в Firestore
    var documentsKeys: MutableList<KeysAndSimpleDate> = mutableListOf()
    //endregion
}