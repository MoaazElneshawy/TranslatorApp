package com.moaazelneshawy.kmm.translatorapp.android.translate.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moaazelneshawy.kmm.translatorapp.android.core.theme.LightBlue
import com.moaazelneshawy.kmm.translatorapp.android.translate.presentation.smallSpacerWidth
import com.moaazelneshawy.kmm.translatorapp.core.presentation.UiLanguage

@Composable
fun LanguageDisplay(
    language: UiLanguage,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SmallLanguageIcon(language = language)
        Spacer(modifier = Modifier.width(smallSpacerWidth))
        Text(text = language.language.langName, color = LightBlue)
    }

}