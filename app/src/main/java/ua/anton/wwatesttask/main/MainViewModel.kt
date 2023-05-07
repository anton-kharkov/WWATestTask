package ua.anton.wwatesttask.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import ua.anton.wwatesttask.R
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val remoteConfig = FirebaseRemoteConfig.getInstance()

    fun getRemoteConfig(context: Context) {
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d("MainViewModel_20", "Remote config updated:${it.result}")
                } else {
                    Log.d("MainViewModel_22", "Remote config fetch failed")
                }
            }

        val appType = remoteConfig.getBoolean("appType")
        Log.d("MainViewModel_33", appType.toString())
    }
}