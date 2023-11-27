package com.enti.dostres.cdi.abelpujol.modulodosfirabase.fragments.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.enti.dostres.cdi.abelpujol.modulodosfirabase.R
import com.enti.dostres.cdi.abelpujol.modulodosfirabase.clases.firebase.MyFirebase
import com.enti.dostres.cdi.abelpujol.modulodosfirabase.clases.firebase.MyFirebaseAnalytics
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.Firebase
import com.google.firebase.crashlytics.crashlytics
import com.google.firebase.crashlytics.setCustomKeys

class AppToolbar : Fragment() {

    companion object{
        private lateinit var instance: AppToolbar
        fun get() = instance
    }

    lateinit var toolbar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.component_toolbar, container, false);
        toolbar = view.findViewById(R.id.AppToolbar)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.setNavigationOnClickListener {

        }

        toolbar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId){
                R.id.toolbar_button_test -> {
                    //throw RuntimeException("Test Crash") // Force a crash
                    MyFirebase.crashlytics.logSimpleError("Subnormal Error"){
                        key("Subnormal Name", "Abraham")
                        key("IsSubnormal", true)
                        key("Level of Subnormality", 9001)
                    };
                }
            }
            true
        }
    }

}