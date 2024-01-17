package com.moaazelneshawy.kmm.translatorapp.android.translate.presentation.components

import android.speech.tts.TextToSpeech
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
fun rememberTextToSpeech(): TextToSpeech {

    val context = LocalContext.current
    val tts = remember {
        TextToSpeech(context, null)
    }
    // we should stop and shutdown the object to avoid any leaks
    DisposableEffect(key1 = tts, effect = {
        onDispose {
            tts.stop()
            tts.shutdown()
        }
    })
    return tts

}