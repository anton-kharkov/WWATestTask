package ua.anton.wwatesttask.splash

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import ua.anton.wwatesttask.R
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {

    private val database = FirebaseDatabase.getInstance()
    private val databaseRef = database.getReference("pinterest_link")
    private val remoteConfig = FirebaseRemoteConfig.getInstance()
    private val configSettings = remoteConfigSettings {
        minimumFetchIntervalInSeconds = 0
    }

    fun connectToRemoteConfig(context: Context): LiveData<Boolean> {
        val appTypeLiveData = MutableLiveData<Boolean>()

        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    appTypeLiveData.value = it.result
                    Log.d("MainViewModel_34", "Config params updated: ${it.result}")
                    Toast.makeText(
                        context, "Fetch and activate succeeded",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        context, "Fetch failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        return appTypeLiveData
    }

    fun connectToFirebaseDatabase(): LiveData<String> {
        val urlLiveData = MutableLiveData<String>()

        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.getValue(String::class.java)
                urlLiveData.value = value
                Log.d("MainViewModel_57", "Value is: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("MainViewModel_61", "Failed to read value.", error.toException())
            }
        })

        return urlLiveData
    }
}