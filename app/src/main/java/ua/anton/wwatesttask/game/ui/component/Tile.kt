package ua.anton.wwatesttask.game.ui.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import ua.anton.wwatesttask.R
import ua.anton.wwatesttask.ui.thame.Padding

@Composable
fun Tile(number: Int, size: Dp, dx: Int = 0, dy: Int = 0) {

    val x by animateDpAsState(targetValue = Dp(dx * size.value))
    val y by animateDpAsState(targetValue = Dp(dy * size.value))

    TileBox(
        modifier = Modifier
            .size(size)
            .padding(Padding.small)
            .absoluteOffset(x, y)
    ) {
        var textSize by remember { mutableStateOf(56.sp) }

        if (number != 0) Text(
            text = "$number",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = textSize,
            fontFamily = FontFamily(Font(R.font.neon_zone_font)),
            maxLines = 1,
            softWrap = false,
            onTextLayout = { textLayoutResult ->
                if (textLayoutResult.hasVisualOverflow) {
                    textSize *= 0.9
                }
            },
            color =
            if (number > 256) MaterialTheme.colors.surface
            else MaterialTheme.colors.onSurface
        )
    }
}