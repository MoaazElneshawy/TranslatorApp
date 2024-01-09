package com.moaazelneshawy.kmm.translatorapp.core.presentation

import com.moaazelneshawy.kmm.translatorapp.core.domain.Language

expect class UiLanguage {
    val language: Language

    companion object {
        fun byCode(langCode: String): UiLanguage
        val allLanguages: List<UiLanguage>
    }
}