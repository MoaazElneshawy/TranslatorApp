package com.moaazelneshawy.kmm.translatorapp.translate.domain

import com.moaazelneshawy.kmm.translatorapp.core.domain.Language
import com.moaazelneshawy.kmm.translatorapp.core.util.Resource
import com.moaazelneshawy.kmm.translatorapp.translate.domain.client.TranslateClient
import com.moaazelneshawy.kmm.translatorapp.translate.domain.client.TranslateException
import com.moaazelneshawy.kmm.translatorapp.translate.domain.history.HistoryDataSource
import com.moaazelneshawy.kmm.translatorapp.translate.domain.history.HistoryItem

class TranslateUseCase(
    val client: TranslateClient,
    val dataSource: HistoryDataSource
) {
    suspend fun execute(
        fromLanguage: Language,
        textToTranslate: String,
        toLanguage: Language
    ): Resource<String> {
        return try {
            val translatedText = client.translate(
                fromLanguage, textToTranslate, toLanguage
            )

            dataSource.insertHistory(
                HistoryItem(
                    null,
                    fromLanguage.langCode,
                    textToTranslate,
                    toLanguage.langCode,
                    translatedText
                )
            )

            Resource.Success(translatedText)
        } catch (e: TranslateException) {
            Resource.Error(e)
        }
    }
}