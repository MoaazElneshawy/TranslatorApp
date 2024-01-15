package com.moaazelneshawy.kmm.translatorapp.android.translate.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// we use composed{} as it's stateful which can changed according to state
// like changing it base on the theme light or dark
fun Modifier.gradientSurface(): Modifier = composed {
    if (isSystemInDarkTheme()) {
        Modifier.background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFF23272E),
                    Color(0xFF212329)
                )
            )
        )
    } else {
        Modifier.background(MaterialTheme.colors.surface)
    }
}