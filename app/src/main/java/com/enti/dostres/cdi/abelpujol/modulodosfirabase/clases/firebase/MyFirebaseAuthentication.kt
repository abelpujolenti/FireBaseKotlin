package com.enti.dostres.cdi.abelpujol.modulodosfirabase.clases.firebase

import android.app.Application
import com.enti.dostres.cdi.abelpujol.modulodosfirabase.R
import com.enti.dostres.cdi.abelpujol.modulodosfirabase.clases.firebase.models.DataBaseUser
import com.google.firebase.auth.FirebaseAuth

class MyFirebaseAuthentication (val appContext: Application) {

    private val firebaseAuthentication = FirebaseAuth.getInstance()
    private var currentUser: DataBaseUser? = null

    fun IsLoginActive() = GetUser() != null;

    fun SetCurrentUser(newUser: DataBaseUser){
        currentUser = newUser
    }

    fun GetUser() = currentUser

    fun GetAuthenticationDatabaseUser(): DataBaseUser? {

        firebaseAuthentication.currentUser?.let { user ->
            val dataBaseUser = DataBaseUser(
                id = user.uid,
                email = user.email,
                username = user.displayName,
                photoPath = user.photoUrl.toString()
            )
            return dataBaseUser
        }
        return null
    }
}