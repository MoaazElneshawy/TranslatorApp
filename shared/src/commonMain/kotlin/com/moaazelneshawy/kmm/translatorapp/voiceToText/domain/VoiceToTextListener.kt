package com.moaazelneshawy.kmm.translatorapp.voiceToText.domain

import com.moaazelneshawy.kmm.translatorapp.core.util.CommonStateFlow

interface VoiceToTextListener {

    val state : CommonStateFlow<VoiceToTextState>
    fun startListening(languageCode: String)
    fun stopListening()
    fun cancel()
    fun reset()

}