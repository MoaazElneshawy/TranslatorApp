package com.moaazelneshawy.kmm.translatorapp.translate.presentation

import com.moaazelneshawy.kmm.translatorapp.core.presentation.UiLanguage
import com.moaazelneshawy.kmm.translatorapp.core.util.Resource
import com.moaazelneshawy.kmm.translatorapp.core.util.toCommonStateFlow
import com.moaazelneshawy.kmm.translatorapp.translate.domain.TranslateUseCase
import com.moaazelneshawy.kmm.translatorapp.translate.domain.client.TranslateException
import com.moaazelneshawy.kmm.translatorapp.translate.domain.history.HistoryDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch


class TranslateViewModel(
    private val translate: TranslateUseCase,
    private val historyDataSource: HistoryDataSource,
    private val coroutineScope: CoroutineScope?
) {

    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(TranslateState())
    val state = combine(
        _state,
        historyDataSource.getHistory()
    ) { state, history ->
        if(state.history != history) {
            state.copy(
                history = history.mapNotNull { item ->
                    UiHistoryItem(
                        id = item.id ?: return@mapNotNull null,
                        fromText = item.fromText,
                        toText = item.toText,
                        fromLanguage = UiLanguage.byCode(item.fromLanguageCode),
                        toLanguage = UiLanguage.byCode(item.toLanguageCode)
                    )
                }
            )
        } else state
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TranslateState())
        .toCommonStateFlow()

    private var translateJob: Job? = null

    fun onEvent(event: TranslateEvent) {
        when(event) {
            is TranslateEvent.ChangeTranslationText -> {
                _state.update { it.copy(
                    fromText = event.text
                ) }
            }
            is TranslateEvent.ChooseFromLanguage -> {
                _state.update { it.copy(
                    isChoosingFromLanguage = false,
                    fromLanguage = event.uiLanguage
                ) }
            }
            is TranslateEvent.ChooseToLanguage -> {
                val newState = _state.updateAndGet { it.copy(
                    isChoosingToLanguage = false,
                    toLanguage = event.uiLanguage
                ) }
                translate(newState)
            }
            TranslateEvent.CloseTranslation -> {
                _state.update { it.copy(
                    isTranslating = false,
                    fromText = "",
                    toText = null
                ) }
            }
            TranslateEvent.EditTranslation -> {
                if(state.value.toText != null) {
                    _state.update { it.copy(
                        toText = null,
                        isTranslating = false
                    ) }
                }
            }
            TranslateEvent.OnError -> {
                _state.update { it.copy(error = null) }
            }
            TranslateEvent.OpenFromLanguageDropDown -> {
                _state.update { it.copy(
                    isChoosingFromLanguage = true
                ) }
            }
            TranslateEvent.OpenToLanguageDropDown -> {
                _state.update { it.copy(
                    isChoosingToLanguage = true
                ) }
            }
            is TranslateEvent.SelectHistoryItem -> {
                translateJob?.cancel()
                _state.update { it.copy(
                    fromText = event.historyItem.fromText,
                    toText = event.historyItem.toText,
                    isTranslating = false,
                    fromLanguage = event.historyItem.fromLanguage,
                    toLanguage = event.historyItem.toLanguage
                ) }
            }
            TranslateEvent.StopChoosingLanguage -> {
                _state.update { it.copy(
                    isChoosingFromLanguage = false,
                    isChoosingToLanguage = false
                ) }
            }
            is TranslateEvent.SubmitVoiceResult -> {
                _state.update { it.copy(
                    fromText = event.result ?: it.fromText,
                    isTranslating = if(event.result != null) false else it.isTranslating,
                    toText = if(event.result != null) null else it.toText
                ) }
            }
            TranslateEvent.SwapLanguage -> {
                _state.update { it.copy(
                    fromLanguage = it.toLanguage,
                    toLanguage = it.fromLanguage,
                    fromText = it.toText ?: "",
                    toText = if(it.toText != null) it.fromText else null
                ) }
            }
            TranslateEvent.Translate -> translate(state.value)
            else -> Unit
        }
    }

    private fun translate(state: TranslateState) {
        if(state.isTranslating || state.fromText.isBlank()) {
            return
        }

        translateJob = viewModelScope.launch {
            _state.update { it.copy(
                isTranslating = true
            ) }
            val result = translate.execute(
                fromLanguage = state.fromLanguage.language,
                textToTranslate = state.fromText,
                toLanguage = state.toLanguage.language
            )
            when(result) {
                is Resource.Success -> {
                    _state.update { it.copy(
                        isTranslating = false,
                        toText = result.data
                    ) }
                }
                is Resource.Error -> {
                    _state.update { it.copy(
                        isTranslating = false,
                        error = (result.ex as? TranslateException)?.error
                    ) }
                }
            }
        }
    }
}