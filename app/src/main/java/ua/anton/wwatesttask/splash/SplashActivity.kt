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
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import ua.anton.wwatesttask.R
import ua.anton.wwatesttask.game.GameActivity
import ua.anton.wwatesttask.main.MainActivity
import ua.anton.wwatesttask.ui.thame.WWATestTaskTheme

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : ComponentActivity() {

    private val splashViewModel: SplashViewModel by viewModels()

    private val animationTween = 1000
    private val initialLogoValue = 1f
    private val targetLogoValue = 0.8f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WWATestTaskTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ActivityUi(initialLogoValue, targetLogoValue, animationTween)
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

@Composable
private fun ActivityUi(
    initialLogoValue: Float,
    targetLogoValue: Float,
    animationTween: Int
) {
    val infiniteTransition = rememberInfiniteTransition()
    val animatedScale by infiniteTransition.animateFloat(
        initialValue = initialLogoValue,
        targetValue = targetLogoValue,
        animationSpec = infiniteRepeatable(
            animation = tween(animationTween, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.ic_logo),
            contentDescription = "",
            modifier = Modifier
                .width(400.dp)
                .height(400.dp)
                .graphicsLayer {
                    scaleX = animatedScale
                    scaleY = animatedScale
                }
        )
    }
}