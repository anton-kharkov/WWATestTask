package ua.anton.wwatesttask.game

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import ua.anton.wwatesttask.ui.thame.WWATestTaskTheme

@AndroidEntryPoint
class GameActivity : ComponentActivity() {

    private val gameViewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WWATestTaskTheme {
                Scaffold{
                    val score by gameViewModel.score.collectAsState()
                    val highScore by gameViewModel.highScore.collectAsState()
                    val grid by gameViewModel.grid

                    GameScreen(
                        score,
                        highScore,
                        Modifier.padding(it),
                        gameViewModel.gameLogic,
                        grid,
                        gameViewModel::newGame
                    )
                }
            }
        }
    }
}