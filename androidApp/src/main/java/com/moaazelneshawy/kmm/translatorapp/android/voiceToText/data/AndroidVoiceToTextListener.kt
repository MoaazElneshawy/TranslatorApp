package com.moaazelneshawy.kmm.translatorapp.android.voiceToText.data

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import com.moaazelneshawy.kmm.translatorapp.core.util.CommonStateFlow
import com.moaazelneshawy.kmm.translatorapp.core.util.toCommonStateFlow
import com.moaazelneshawy.kmm.translatorapp.voiceToText.domain.VoiceToTextListener
import com.moaazelneshawy.kmm.translatorapp.voiceToText.domain.VoiceToTextState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class AndroidVoiceToTextListener(private val app: Application)
    : VoiceToTextListener, RecognitionListener {

    private val speechRecognizer = SpeechRecognizer.createSpeechRecognizer(app)

    private val _state = MutableStateFlow<VoiceToTextState>(VoiceToTextState())
    override val state: CommonStateFlow<VoiceToTextState>
        get() = _state.toCommonStateFlow()

    override fun startListening(languageCode: String) {
        _state.update { VoiceToTextState() }
        if (!SpeechRecognizer.isRecognitionAvailable(app)) {
            _state.update { it.copy(error = "Speech Recognition is not available !") }
            return
        }
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, languageCode)
        }

        speechRecognizer.setRecognitionListener(this)
        speechRecognizer.startListening(intent)
        _state.update {
            it.copy(isSpeaking = true)
        }
    }

    override fun stopListening() {
        _state.update { VoiceToTextState() }
        speechRecognizer.stopListening()
    }

    override fun cancel() {
        speechRecognizer.cancel()
    }

    override fun reset() {
        _state.value = VoiceToTextState()
    }

    override fun onReadyForSpeech(p0: Bundle?) {
        _state.update { it.copy(error = null) }
    }

    override fun onBeginningOfSpeech() = Unit

    override fun onRmsChanged(rms: Float) {
        _state.update {
            it.copy(
                powerRatio = rms * (1f / (12f - (-2f)))
            )
        }
    }

    override fun onBufferReceived(p0: ByteArray?) = Unit

    override fun onEndOfSpeech() {
        _state.update {
            it.copy(isSpeaking = false)
        }
    }

    override fun onError(errorCode: Int) {
        _state.update {
            it.copy(error = "Error $errorCode")
        }
    }

    override fun onResults(results: Bundle?) {
        results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            ?.getOrNull(0)
            ?.let { text ->
                _state.update {
                    it.copy(result = text)
                }
            }
    }

    override fun onPartialResults(p0: Bundle?) = Unit
    override fun onEvent(p0: Int, p1: Bundle?) = Unit
}