package ua.anton.wwatesttask.ui.thame

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ua.anton.wwatesttask.R

val Typography = androidx.compose.material.Typography(
    body1 = TextStyle(
        fontFamily = FontFamily(Font(R.font.neon_zone_font)),
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),

    button = TextStyle(
        fontFamily = FontFamily(Font(R.font.neon_zone_font)),
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),

    caption = TextStyle(
        fontFamily = FontFamily(Font(R.font.neon_zone_font)),
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
)