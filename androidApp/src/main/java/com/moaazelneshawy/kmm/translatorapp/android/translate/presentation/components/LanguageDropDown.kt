package com.moaazelneshawy.kmm.translatorapp.android.translate.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.moaazelneshawy.kmm.translatorapp.android.R
import com.moaazelneshawy.kmm.translatorapp.android.core.theme.LightBlue
import com.moaazelneshawy.kmm.translatorapp.android.translate.presentation.largeIconSize
import com.moaazelneshawy.kmm.translatorapp.android.translate.presentation.largeSpacerWidth
import com.moaazelneshawy.kmm.translatorapp.android.translate.presentation.padding_10
import com.moaazelneshawy.kmm.translatorapp.core.presentation.UiLanguage

@Composable
fun LanguageDropDown(
    selectedLanguage: UiLanguage,
    isOpen: Boolean,
    onClick: () -> Unit,
    onDismiss: () -> Unit,
    onSelectLanguage: (UiLanguage) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        DropdownMenu(
            expanded = isOpen, onDismissRequest = onDismiss
        ) {
            // the drop down items
            UiLanguage.allLanguages.forEach { language ->
                LanguageDropDownMenuItem(
                    language = language,
                    onClick = { onSelectLanguage.invoke(language) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        // the view behind the dropDown
        Row(
            modifier = Modifier
                .clickable(onClick = onClick)
                .padding(padding_10),
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                model = selectedLanguage.drawableRes,
                contentDescription = selectedLanguage.language.langName,
                modifier = Modifier.size(largeIconSize)
            )
            Spacer(modifier = Modifier.width(largeSpacerWidth))
            Text(
                text = selectedLanguage.language.langName,
                color = LightBlue
            )
            Icon(
                imageVector = if (isOpen) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                contentDescription =
                if (isOpen) stringResource(id = R.string.close)
                else stringResource(id = R.string.open),
                tint = LightBlue
            )
        }
    }
}