package com.enti.dostres.cdi.abelpujol.modulodosfirabase.clases.firebase.models

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class DataBaseMessage(
    override var id: String? = null,
    var userId: String? = null,
    var message: String? = null,
    var imageUrl: String? = null,
    @ServerTimestamp var timeStamp: Date? = null
): DataBaseData {
    override fun GetTable() = "Messages"
}
