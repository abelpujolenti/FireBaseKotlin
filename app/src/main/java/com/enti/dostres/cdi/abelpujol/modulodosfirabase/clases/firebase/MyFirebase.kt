package com.enti.dostres.cdi.abelpujol.modulodosfirabase.clases.firebase

import android.app.Application

class MyFirebase  {

    companion object{

        lateinit var analytics: MyFirebaseAnalytics;
        lateinit var authentication: MyFirebaseAuthentication
        val crashlytics = MyCrashlytics();
        val dataBase = MyFirebaseDatabase();
        val storage = MyFirebaseStorage();
        val remoteConfig = MyFirebaseRemoteConfig();

        fun init(appContext: Application)
        {
            authentication = MyFirebaseAuthentication(appContext)
            analytics = MyFirebaseAnalytics(appContext);
        }
    }
}