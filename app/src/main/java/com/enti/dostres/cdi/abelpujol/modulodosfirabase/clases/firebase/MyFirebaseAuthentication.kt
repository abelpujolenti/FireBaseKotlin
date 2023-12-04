package com.enti.dostres.cdi.abelpujol.modulodosfirabase.clases.firebase

import android.app.Application
import com.enti.dostres.cdi.abelpujol.modulodosfirabase.R
import com.google.firebase.auth.FirebaseAuth

class MyFirebaseAuthentication (val appContext: Application) {

    private val firebaseAuthentication = FirebaseAuth.getInstance()

    fun GetUserName() = firebaseAuthentication.currentUser?.displayName ?: appContext.getString(R.string.unknown_user)

}