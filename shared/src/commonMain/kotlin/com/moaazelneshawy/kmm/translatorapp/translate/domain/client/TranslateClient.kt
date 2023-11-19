package com.moaazelneshawy.kmm.translatorapp.translate.domain.client

import com.moaazelneshawy.kmm.translatorapp.core.domain.Language

interface TranslateClient {
    suspend fun translate(
        textToTranslate: String,
        fromLanguage: Language,
        toLanguage: Language
    ): String
}