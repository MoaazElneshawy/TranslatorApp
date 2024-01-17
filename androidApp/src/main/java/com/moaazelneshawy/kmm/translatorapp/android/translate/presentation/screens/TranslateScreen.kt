package com.moaazelneshawy.kmm.translatorapp.android.translate.presentation.screens

import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import com.moaazelneshawy.kmm.translatorapp.android.R
import com.moaazelneshawy.kmm.translatorapp.android.translate.presentation.components.LanguageDropDown
import com.moaazelneshawy.kmm.translatorapp.android.translate.presentation.components.SwapLanguagesButton
import com.moaazelneshawy.kmm.translatorapp.android.translate.presentation.components.TranslateTextField
import com.moaazelneshawy.kmm.translatorapp.android.translate.presentation.components.TranslatedHistoryItems
import com.moaazelneshawy.kmm.translatorapp.android.translate.presentation.components.rememberTextToSpeech
import com.moaazelneshawy.kmm.translatorapp.android.translate.presentation.padding_10
import com.moaazelneshawy.kmm.translatorapp.translate.domain.client.TranslateError
import com.moaazelneshawy.kmm.translatorapp.translate.presentation.TranslateEvent
import com.moaazelneshawy.kmm.translatorapp.translate.presentation.TranslateState
import java.util.Locale

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TranslateScreen(
    state: TranslateState, onEvent: (TranslateEvent) -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = state.error, block = {
        val message = when (state.error) {
            TranslateError.SERVICE_UNAVAILABLE -> context.getString(R.string.service_unavailble)
            TranslateError.CLIENT_ERROR -> context.getString(R.string.client_error)
            TranslateError.SERVER_ERROR -> context.getString(R.string.server_error)
            TranslateError.UNKNOWN_ERROR -> context.getString(R.string.unkown_error)
            else -> null
        }
        message?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            onEvent(TranslateEvent.OnError) // to reset the state after error !
        }
    })
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEvent(TranslateEvent.RecordAudio) },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary,
                modifier = Modifier.size(60.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.mic),
                    contentDescription = stringResource(
                        id = R.string.record_audio
                    )
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->

        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(padding_10),
            verticalArrangement = Arrangement.spacedBy(padding_10),
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

                item {
                    val clipboardManager = LocalClipboardManager.current
                    val keyboardController = LocalSoftwareKeyboardController.current
                    val tts = rememberTextToSpeech()
                    TranslateTextField(
                        fromText = state.fromText,
                        toText = state.toText,
                        isTranslating = state.isTranslating,
                        fromLanguage = state.fromLanguage,
                        toLanguage = state.toLanguage,
                        onTranslateClick = {
                            keyboardController?.hide()
                            onEvent(TranslateEvent.Translate)
                        },
                        onTextChange = {
                            onEvent(TranslateEvent.ChangeTranslationText(it))
                        },
                        onCopyClick = { text ->
                            clipboardManager.setText(
                                buildAnnotatedString {
                                    append(text)
                                }
                            )
                            Toast.makeText(
                                context,
                                context.getString(
                                    R.string.copied_to_clipboard
                                ),
                                Toast.LENGTH_LONG
                            ).show()
                        },
                        onCloseClick = {
                            onEvent(TranslateEvent.CloseTranslation)
                        },
                        onSpeakerClick = {
                            tts.language = state.toLanguage.toLocale() ?: Locale.ENGLISH
                            tts.speak(
                                state.toText,
                                TextToSpeech.QUEUE_FLUSH,
                                null,
                                null
                            )

                        },
                        onTextFieldClick = {
                            onEvent(TranslateEvent.EditTranslation)
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                item {
                    if (state.history.isNotEmpty()) {
                        Text(
                            text = stringResource(id = R.string.history),
                            color = MaterialTheme.colors.onSurface,
                            style = MaterialTheme.typography.h1
                        )
                    }
                }

                items(state.history) { item ->
                    TranslatedHistoryItems(
                        item = item,
                        onClick = { onEvent(TranslateEvent.SelectHistoryItem(item)) },
                        modifier = Modifier.fillMaxWidth()
                    )

                }
            }

        )

    }

}