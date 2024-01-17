package com.moaazelneshawy.kmm.translatorapp.android.translate.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import com.moaazelneshawy.kmm.translatorapp.android.translate.presentation.smallIconSize
import com.moaazelneshawy.kmm.translatorapp.core.presentation.UiLanguage

@Composable
fun SmallLanguageIcon(
    language: UiLanguage,
    modifier: Modifier = Modifier
) {

    AsyncImage(
        model = language.drawableRes, contentDescription = language.language.langName,
        modifier = modifier.size(smallIconSize)
    )

}