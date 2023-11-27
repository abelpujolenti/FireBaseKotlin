package com.enti.dostres.cdi.abelpujol.modulodosfirabase

import android.app.Application
import com.enti.dostres.cdi.abelpujol.modulodosfirabase.clases.firebase.MyFirebase

class MyApp : Application() {

    companion object{

        private lateinit var instance : MyApp;
        public fun get() = instance;

    }


    override fun onCreate()
    {
        super.onCreate();
        instance = this;
        MyFirebase.init(this);
        MyFirebase.analytics.logOpenApp();
    }

}