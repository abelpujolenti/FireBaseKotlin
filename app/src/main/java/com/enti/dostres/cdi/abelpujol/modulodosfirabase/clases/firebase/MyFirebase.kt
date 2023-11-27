package com.enti.dostres.cdi.abelpujol.modulodosfirabase.clases.firebase

import android.app.Application

class MyFirebase  {

    companion object{

        lateinit var analytics: MyFirebaseAnalytics

        fun init(appContext: Application)
        {
               analytics = MyFirebaseAnalytics(appContext)
        }
    }
}