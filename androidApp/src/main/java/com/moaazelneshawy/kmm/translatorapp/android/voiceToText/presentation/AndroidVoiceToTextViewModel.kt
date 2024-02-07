package com.moaazelneshawy.kmm.translatorapp.android.voiceToText.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moaazelneshawy.kmm.translatorapp.voiceToText.domain.VoiceToTextParserListener
import com.moaazelneshawy.kmm.translatorapp.voiceToText.presentation.VoiceToTextEvent
import com.moaazelneshawy.kmm.translatorapp.voiceToText.presentation.VoiceToTextViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidVoiceToTextViewModel @Inject constructor(
    private val voiceToTextParserListener: VoiceToTextParserListener
) : ViewModel() {


    private val viewModel by lazy {
        VoiceToTextViewModel(
            voiceToTextParserListener,
            viewModelScope
        )
    }

    val state = viewModel.state

    fun onEvent(event: VoiceToTextEvent) {
        viewModel.onEvent(event)
    }
}