package com.moaazelneshawy.kmm.translatorapp.translate.presentation

import com.moaazelneshawy.kmm.translatorapp.core.presentation.UiLanguage
import com.moaazelneshawy.kmm.translatorapp.translate.domain.client.TranslateError

data class TranslateState(
    val fromText: String = "",
    val toText: String? = null,
    val isTranslating: Boolean = false,
    val fromLanguage: UiLanguage = UiLanguage.byCode("en"),
    val toLanguage: UiLanguage = UiLanguage.byCode("ar"),
    val isChoosingFromLanguage: Boolean = false,
    val isChoosingToLanguage: Boolean = false,
    val error: TranslateError? = null,
    val history: List<UiHistoryItem> = emptyList()
)
