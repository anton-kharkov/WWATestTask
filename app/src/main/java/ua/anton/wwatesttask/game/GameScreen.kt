package ua.anton.wwatesttask.game

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ua.anton.wwatesttask.game.model.GameLogic
import ua.anton.wwatesttask.game.ui.component.GameBoard
import ua.anton.wwatesttask.game.ui.component.TopControlPanel

@Composable
fun GameScreen(
    score: Int,
    bestScore: Int,
    modifier: Modifier,
    gameLogic: GameLogic,
    gridTile: List<IntArray>,
    onNewGame: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 35.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        TopControlPanel(score, bestScore, modifier, onNewGame)
        Spacer(modifier = Modifier.height(40.dp))
        GameBoard(gridTile, gameLogic::swipe)
    }
}

