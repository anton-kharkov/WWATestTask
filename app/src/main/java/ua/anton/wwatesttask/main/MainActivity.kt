package ua.anton.wwatesttask.main

import android.app.AlertDialog
import android.content.Context
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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dagger.hilt.android.AndroidEntryPoint
import ua.anton.wwatesttask.R
import ua.anton.wwatesttask.site.SiteActivity
import ua.anton.wwatesttask.ui.thame.WWATestTaskTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    private val animationTween = 1500
    private val initialLogoValue = 1f
    private val targetLogoValue = 0.4f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
            mainViewModel.getRemoteConfig(this)

            setContent {
                WWATestTaskTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
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
                                    .graphicsLayer(
                                        scaleX = logoAnimation(),
                                        scaleY = logoAnimation()
                                    )
                            )
                            Text(
                                text = getString(R.string.loading),
                                fontSize = 30.sp,
                                color = Color.Black,
                            )
                        }
                    }
                }
            }

//            this.startActivity(Intent(this, SiteActivity::class.java))
        }
    }

    @Composable
    private fun logoAnimation(): Float {
        val infiniteTransition = rememberInfiniteTransition()
        val animatedScale by infiniteTransition.animateFloat(
            initialValue = initialLogoValue,
            targetValue = targetLogoValue,
            animationSpec = infiniteRepeatable(
                animation = tween(animationTween, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            )
        )
        return animatedScale
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return networkCapabilities != null &&
                (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                        || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
    }
}