package ua.anton.wwatesttask.site

import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import dagger.hilt.android.AndroidEntryPoint
import ua.anton.wwatesttask.ui.thame.WWATestTaskTheme

@AndroidEntryPoint
class SiteActivity : ComponentActivity() {

    private val siteViewModel: SiteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        siteViewModel.connectToFirebaseDatabase()

        setContent {
            WWATestTaskTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    AndroidView(factory = {
                        WebView(it).apply {
                            layoutParams = ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                            )
                            webViewClient = WebViewClient()
                            settings.apply {
                                javaScriptEnabled = true
                            }
                            loadUrl(siteViewModel.getSiteUrl())
                        }
                    }, update = {
                        it.loadUrl(siteViewModel.getSiteUrl())
                    })
                }
            }
        }
    }
}