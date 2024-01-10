package com.moaazelneshawy.kmm.translatorapp.android.core.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color
import com.moaazelneshawy.kmm.translatorapp.core.presentation.Colors

val LightBlue = Color(Colors.LightBlue)
val LightBlueGray = Color(Colors.LightBlueGrey)
val TextBlack = Color(Colors.TextBlack)
val AccentViolet = Color(Colors.AccentViolet)
val DarkGray = Color(Colors.DarkGrey)

val lightThemeColors = lightColors(
    primary = AccentViolet,
    background = LightBlueGray,
    onPrimary = Color.White,
    onBackground = TextBlack,
    surface = Color.White,
    onSurface = TextBlack
)
val darkThemeColors = darkColors(
    primary = AccentViolet,
    background = DarkGray,
    onBackground = Color.White,
    onPrimary = Color.White,
    surface = DarkGray,
    onSurface = Color.White
)