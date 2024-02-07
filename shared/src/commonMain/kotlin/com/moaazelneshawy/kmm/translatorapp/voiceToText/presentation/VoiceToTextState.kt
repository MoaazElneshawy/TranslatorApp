package com.moaazelneshawy.kmm.translatorapp.voiceToText.presentation

data class VoiceToTextState(
    val powerRatio : List<Float> = emptyList(),
    val spokenText:String = "",
    val canRecord : Boolean = false,
    val recordError : String? = null,
    val displayState : DisplayState? = null
)

enum class DisplayState{
    WAITING_TO_TALK,
    SPEAKING,
    DISPLAYING_RESULT,
    ERROR
}
