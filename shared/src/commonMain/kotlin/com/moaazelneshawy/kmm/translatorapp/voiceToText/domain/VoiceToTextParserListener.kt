package com.moaazelneshawy.kmm.translatorapp.voiceToText.domain

import com.moaazelneshawy.kmm.translatorapp.core.util.CommonStateFlow

interface VoiceToTextParserListener {

    val state : CommonStateFlow<VoiceToTextParserState>
    fun startListening(languageCode: String)
    fun stopListening()
    fun cancel()
    fun reset()

}