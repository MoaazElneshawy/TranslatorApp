package com.moaazelneshawy.kmm.translatorapp.translate.presentation

import com.moaazelneshawy.kmm.translatorapp.core.presentation.UiLanguage

sealed class TranslateEvent {
    data class ChooseFromLanguage(val uiLanguage: UiLanguage) : TranslateEvent()
    data class ChooseToLanguage(val uiLanguage: UiLanguage) : TranslateEvent()

    object StopChoosingLanguage : TranslateEvent()
    object SwapLanguage : TranslateEvent()
    data class ChangeTranslationText(val text: String) : TranslateEvent()
    object Translate : TranslateEvent()
    object OpenFromLanguageDropDown : TranslateEvent()
    object OpenToLanguageDropDown : TranslateEvent()
    object CloseTranslation : TranslateEvent()
    data class SelectHistoryItem(val historyItem: UiHistoryItem) : TranslateEvent()
    object EditTranslation : TranslateEvent()
    object RecordAudio : TranslateEvent()

    data class SubmitVoiceResult(val result: String?) : TranslateEvent()

    object OnError : TranslateEvent()
}
