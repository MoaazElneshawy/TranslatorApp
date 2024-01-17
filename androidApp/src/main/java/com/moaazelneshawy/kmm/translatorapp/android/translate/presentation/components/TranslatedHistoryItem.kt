package com.moaazelneshawy.kmm.translatorapp.android.translate.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import com.moaazelneshawy.kmm.translatorapp.android.core.theme.LightBlue
import com.moaazelneshawy.kmm.translatorapp.android.translate.presentation.elevation
import com.moaazelneshawy.kmm.translatorapp.android.translate.presentation.largeSpacerHeight
import com.moaazelneshawy.kmm.translatorapp.android.translate.presentation.padding_10
import com.moaazelneshawy.kmm.translatorapp.android.translate.presentation.roundCornerSize
import com.moaazelneshawy.kmm.translatorapp.android.translate.presentation.smallSpacerWidth
import com.moaazelneshawy.kmm.translatorapp.translate.presentation.UiHistoryItem

@Composable
fun TranslatedHistoryItems(
    item: UiHistoryItem,
    onClick: () -> Unit,
    modifier: Modifier
) {

    Column(
        modifier = modifier
            .shadow(
                elevation = elevation,
                shape = RoundedCornerShape(roundCornerSize)
            )
            .clip(RoundedCornerShape(roundCornerSize))
            .gradientSurface()
            .clickable(onClick = onClick)
            .padding(padding_10)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SmallLanguageIcon(language = item.fromLanguage)
            Spacer(modifier = Modifier.width(smallSpacerWidth))
            Text(
                text = item.fromText,
                color = LightBlue,
                style = MaterialTheme.typography.body2
            )
        }
        Spacer(modifier = Modifier.height(largeSpacerHeight))
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SmallLanguageIcon(language = item.toLanguage)
            Spacer(modifier = Modifier.width(smallSpacerWidth))
            Text(
                text = item.toText,
                color = MaterialTheme.colors.onSurface,
                fontWeight = FontWeight.Medium,
                style = MaterialTheme.typography.body1
            )
        }
    }

}