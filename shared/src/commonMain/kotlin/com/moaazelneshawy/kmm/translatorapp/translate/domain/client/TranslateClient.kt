package com.moaazelneshawy.kmm.translatorapp.translate.domain.client

import com.moaazelneshawy.kmm.translatorapp.core.domain.Language

interface TranslateClient {
    suspend fun translate(
        fromLanguage: Language,
        fromText: String,
        toLanguage: Language
    ): String
}