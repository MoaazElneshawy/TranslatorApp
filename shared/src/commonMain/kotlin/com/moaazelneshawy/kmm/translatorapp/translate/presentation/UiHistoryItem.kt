package com.moaazelneshawy.kmm.translatorapp.translate.presentation

import com.moaazelneshawy.kmm.translatorapp.core.presentation.UiLanguage

data class UiHistoryItem(
    val id: Long,
    val fromText: String,
    val toText: String,
    val fromLanguage: UiLanguage,
    val toLanguage: UiLanguage
)
