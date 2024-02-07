package com.moaazelneshawy.kmm.translatorapp.voiceToText.presentation

import com.moaazelneshawy.kmm.translatorapp.core.util.toCommonStateFlow
import com.moaazelneshawy.kmm.translatorapp.voiceToText.domain.VoiceToTextParserListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class VoiceToTextViewModel(
    private val voiceParserListener: VoiceToTextParserListener,
    coroutineScope: CoroutineScope? = null
) {

    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow<VoiceToTextState>(VoiceToTextState())

    val state = _state.combine(voiceParserListener.state) { state, listenerState ->
        state.copy(
            spokenText = listenerState.result,
            recordError = if (state.canRecord) listenerState.error else "Can't record without permission !",
            displayState = when {
                !state.canRecord || listenerState.error != null -> DisplayState.ERROR
                listenerState.result.isNotBlank() && listenerState.isSpeaking.not() -> DisplayState.DISPLAYING_RESULT
                listenerState.isSpeaking -> DisplayState.SPEAKING
                else -> DisplayState.WAITING_TO_TALK
            }
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), VoiceToTextState())
        .toCommonStateFlow()

    init {
        viewModelScope.launch {
            while (true) {
                if (state.value.displayState == DisplayState.SPEAKING) {
                    _state.update {
                        it.copy(
                            powerRatio = it.powerRatio + voiceParserListener.state.value.powerRatio
                        )
                    }
                    delay(50L)
                }
            }
        }
    }

    private fun toggleRecording(languageCode: String) {
        _state.update { it.copy(powerRatio = emptyList()) }
        voiceParserListener.cancel()
        if (state.value.displayState == DisplayState.SPEAKING) {
            voiceParserListener.stopListening()
        } else {
            voiceParserListener.startListening(languageCode)
        }
    }

    fun onEvent(event: VoiceToTextEvent) {
        when (event) {
            is VoiceToTextEvent.PermissionResult -> {
                _state.update {
                    it.copy(
                        canRecord = event.isGranted
                    )
                }
            }

            VoiceToTextEvent.Reset -> {
                voiceParserListener.reset()
                _state.update { VoiceToTextState() }
            }

            is VoiceToTextEvent.ToggleRecording -> {
                toggleRecording(event.languageCode)
            }

            else -> Unit
        }
    }
}