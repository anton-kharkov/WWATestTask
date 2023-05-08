package ua.anton.wwatesttask.game.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import ua.anton.wwatesttask.game.model.GameLogic
import ua.anton.wwatesttask.ui.thame.Padding

@Composable
fun GameBoard(
    gridTile: List<IntArray>,
    onSwipe: (GameLogic.Swipe) -> Unit = {}
) {
    BoxWithConstraints(
        modifier = Modifier
            .aspectRatio(1f)
            .border(
                width = Dp.Hairline,
                color = MaterialTheme.colors.primary,
                shape = MaterialTheme.shapes.large
            )
            .padding(Padding.medium)
            .pointerInput(Unit)
            {
                var direction: GameLogic.Swipe? = null
                detectHorizontalDragGestures(
                    onDragEnd = { direction?.let { onSwipe(it) } },
                ) { change, x ->
                    change.consume()
                    when {
                        x > 50 -> direction = GameLogic.Swipe.RIGHT
                        x < -50 -> direction = GameLogic.Swipe.LEFT
                    }
                }
            }
            .pointerInput(Unit)
            {
                var direction: GameLogic.Swipe? = null
                detectVerticalDragGestures(
                    onDragEnd = { direction?.let { onSwipe(it) } },
                ) { change, y ->
                    change.consume()
                    when {
                        y > 50 -> direction = GameLogic.Swipe.DOWN
                        y < -50 -> direction = GameLogic.Swipe.UP
                    }
                }
            }
    )
    {

        val tileSize = maxWidth / gridTile.size

        for (i in gridTile.indices) {
            for (j in gridTile.indices) {
                Tile(number = gridTile[i][j], size = tileSize, i, j)
            }
        }
    }
}