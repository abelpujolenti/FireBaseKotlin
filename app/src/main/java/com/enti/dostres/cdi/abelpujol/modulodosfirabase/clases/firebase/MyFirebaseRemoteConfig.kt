package com.enti.dostres.cdi.abelpujol.modulodosfirabase.clases.firebase

import com.enti.dostres.cdi.abelpujol.modulodosfirabase.R
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.google.firebase.remoteconfig.remoteConfig

class MyFirebaseRemoteConfig {

    private val remoteConfig = Firebase.remoteConfig

    enum class RemoteKeys(val key: String){
        CurrentTheme("current_theme")
    }

    init {
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 30
        }

        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
        remoteConfig.fetchAndActivate()
    }

    enum class AppTheme(val key: String, val themeId: Int){
        Base("Base", R.style.Theme_ModuloDosFirabase),
        Christmas("Base", R.style.Theme_ModuloDosFirabase_Christmas);

        companion object{
            fun fromKey(key: String): AppTheme{
                return values().find { theme -> theme.key == key } ?: Base
            }
        }
    }

    fun GetTheme() : AppTheme{
        val key = remoteConfig.getString(RemoteKeys.CurrentTheme.key)
        return AppTheme.fromKey(key)
    }
}