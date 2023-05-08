package ua.anton.wwatesttask.game

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ua.anton.wwatesttask.ui.thame.WWATestTaskTheme

class GameActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WWATestTaskTheme {

            }
        }
    }
}