package ua.anton.wwatesttask.splash

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import ua.anton.wwatesttask.R
import ua.anton.wwatesttask.game.GameActivity
import ua.anton.wwatesttask.main.MainActivity
import ua.anton.wwatesttask.splash.component.SplashScreen
import ua.anton.wwatesttask.ui.thame.WWATestTaskTheme

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : ComponentActivity() {

    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WWATestTaskTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    SplashScreen()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        if (!isNetworkAvailable()) {
            val dialog = AlertDialog.Builder(this)
                .setTitle(R.string.no_internet_connection)
                .setMessage(R.string.message_requires_internet_connection)
                .setCancelable(false)
                .setNegativeButton(R.string.close_app) { _, _ ->
                    finish()
                }
                .create()
            dialog.show()
        } else {
            splashViewModel.connectToRemoteConfig(this).observe(this) {
                if (it) {
                    splashViewModel.connectToFirebaseDatabase().observe(this) { url ->
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("url_key", url)
                        startActivity(intent)
                    }
                } else {
                    startActivity(Intent(this, GameActivity::class.java))
                }
            }
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return networkCapabilities != null &&
                (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                        || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
    }
}