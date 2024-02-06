package com.moaazelneshawy.kmm.translatorapp.voiceToText.domain

data class VoiceToTextState(
    val result: String = "",
    val error: String? = null,
    val isSpeaking: Boolean = false,
    val powerRatio: Float = 0f
)
