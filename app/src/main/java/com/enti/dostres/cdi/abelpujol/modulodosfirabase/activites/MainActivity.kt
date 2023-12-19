package com.enti.dostres.cdi.abelpujol.modulodosfirabase.activites

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.enti.dostres.cdi.abelpujol.modulodosfirabase.R
import com.enti.dostres.cdi.abelpujol.modulodosfirabase.clases.firebase.MyFirebase
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {

    private val requestNotificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()) { isGranted ->
           OnNotificationPermissionResponse(isGranted)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(MyFirebase.remoteConfig.GetTheme().themeId)
        setContentView(R.layout.main_activity_screen);

        MyFirebase.crashlytics.logSimpleError("Main Activity Error"){
            key("Subnormal Name", "Abraham")
            key("IsSubnormal", true)
            key("Level of Subnormality", 9001)
        };

        AskNotificationPermission()

    }

    fun OnNotificationPermissionResponse( isGranted: Boolean){
        if (isGranted){
            FirebaseMessaging.getInstance().token
                .addOnSuccessListener { token ->
                    Log.e("Token", "Token::> " + token)
                }
                .addOnFailureListener {
                    //TODO ERROR
                }
        } else {
            //TODO ERROR
        }
    }

    fun AskNotificationPermission(){

        val permission = Manifest.permission.POST_NOTIFICATIONS

        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED){
            FirebaseMessaging.getInstance().token
                .addOnSuccessListener { token ->
                    Log.e("Token", "Token::> " + token)
                }
                .addOnFailureListener {
                    //TODO ERROR
                }
        } else if(shouldShowRequestPermissionRationale(permission)){
            //Move To Setup Screen, can show pop up
        } else {
            requestNotificationPermissionLauncher.launch(permission)
        }
    }
}