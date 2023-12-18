package com.enti.dostres.cdi.abelpujol.modulodosfirabase.activites

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.enti.dostres.cdi.abelpujol.modulodosfirabase.R
import com.enti.dostres.cdi.abelpujol.modulodosfirabase.clases.firebase.MyFirebase

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity_screen);

        MyFirebase.crashlytics.logSimpleError("Main Activity Error"){
            key("Subnormal Name", "Abraham")
            key("IsSubnormal", true)
            key("Level of Subnormality", 9001)
        };

        setTheme(MyFirebase.remoteConfig.GetTheme().themeId)
    }

}