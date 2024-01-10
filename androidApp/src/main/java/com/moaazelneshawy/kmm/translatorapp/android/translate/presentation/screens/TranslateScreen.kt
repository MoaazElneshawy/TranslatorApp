package com.moaazelneshawy.kmm.translatorapp.android.translate.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moaazelneshawy.kmm.translatorapp.android.translate.presentation.components.LanguageDropDown
import com.moaazelneshawy.kmm.translatorapp.android.translate.presentation.components.SwapLanguagesButton
import com.moaazelneshawy.kmm.translatorapp.translate.presentation.TranslateEvent
import com.moaazelneshawy.kmm.translatorapp.translate.presentation.TranslateState

@Composable
fun TranslateScreen(
    state: TranslateState, onEvent: (TranslateEvent) -> Unit
) {

    Scaffold(
        floatingActionButton = {

        }
    ) { paddingValues ->

        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            content = {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        LanguageDropDown(
                            selectedLanguage = state.fromLanguage,
                            isOpen = state.isChoosingFromLanguage,
                            onClick = { onEvent(TranslateEvent.OpenFromLanguageDropDown) },
                            onDismiss = { onEvent(TranslateEvent.StopChoosingLanguage) },
                            onSelectLanguage = { onEvent(TranslateEvent.ChooseFromLanguage(it)) },
                            modifier = Modifier.weight(1f)
                        )
                        SwapLanguagesButton(onClick = { onEvent(TranslateEvent.SwapLanguage) })
                        LanguageDropDown(
                            selectedLanguage = state.toLanguage,
                            isOpen = state.isChoosingToLanguage,
                            onClick = { onEvent(TranslateEvent.OpenToLanguageDropDown) },
                            onDismiss = { onEvent(TranslateEvent.StopChoosingLanguage) },
                            onSelectLanguage = { onEvent(TranslateEvent.ChooseToLanguage(it)) },
                            modifier = Modifier.weight(1f),
                        )
                    }
                }
            })

    }

}