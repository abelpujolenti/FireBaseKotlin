package com.enti.dostres.cdi.abelpujol.modulodosfirabase.clases.firebase

import android.app.Application

class MyFirebase  {

    companion object{

        lateinit var analytics: MyFirebaseAnalytics;
        val crashlytics = MyCrashlytics();

        fun init(appContext: Application)
        {
            analytics = MyFirebaseAnalytics(appContext);
        }
    }
}