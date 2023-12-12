package com.enti.dostres.cdi.abelpujol.modulodosfirabase

import android.app.Activity
import android.app.Application
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.getSystemService
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

    fun CloseKeyboard(focusedView: View)
    {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(focusedView.windowToken, 0)
    }

}