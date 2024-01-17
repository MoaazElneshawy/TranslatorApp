package com.moaazelneshawy.kmm.translatorapp.android.translate.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.moaazelneshawy.kmm.translatorapp.android.translate.presentation.largeIconSize
import com.moaazelneshawy.kmm.translatorapp.android.translate.presentation.largeSpacerWidth
import com.moaazelneshawy.kmm.translatorapp.core.presentation.UiLanguage

@Composable
fun LanguageDropDownMenuItem(
    language: UiLanguage,
    onClick: () -> Unit,
    modifier: Modifier
) {
    DropdownMenuItem(
        onClick = onClick,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = language.drawableRes),
            contentDescription = language.language.langName,
            modifier = Modifier.size(largeIconSize)
        )
        Spacer(modifier = Modifier.width(largeSpacerWidth))
        Text(text = language.language.langName)
    }
}