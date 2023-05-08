package ua.anton.wwatesttask.game.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ua.anton.wwatesttask.R

@Composable
fun TopControlPanel(
    score: Int,
    bestScore: Int,
    modifier: Modifier = Modifier,
    onNewGame: () -> Unit = {}
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = modifier
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .aspectRatio(1 / 1.1f)
                    .weight(1f)
            ) {
                TileBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Text(
                        text = stringResource(R.string.score),
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(Font(R.font.neon_zone_font)),
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        maxLines = 1,
                        softWrap = false,
                    )
                    Text(
                        text = score.toString(),
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(Font(R.font.neon_zone_font)),
                        fontSize = 20.sp,
                        maxLines = 1,
                        softWrap = false,
                    )
                }
            }

            TileBox(
                modifier = Modifier
                    .aspectRatio(1 / 1.1f)
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(R.string.game_name),
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.neon_zone_font)),
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    maxLines = 1,
                    softWrap = false,
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .aspectRatio(1 / 1.1f)
                    .weight(1f)
            ) {
                TileBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Text(
                        text = stringResource(R.string.best_score),
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(Font(R.font.neon_zone_font)),
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        maxLines = 1,
                        softWrap = false,
                    )
                    Text(
                        text = bestScore.toString(),
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(Font(R.font.neon_zone_font)),
                        fontSize = 20.sp,
                        maxLines = 1,
                        softWrap = false,
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        TileBox(
            modifier = Modifier
                .clickable { onNewGame() }
        ) {
            Text(
                text = stringResource(R.string.new_game),
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.neon_zone_font)),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                maxLines = 1,
                softWrap = false,
            )
        }
    }
}