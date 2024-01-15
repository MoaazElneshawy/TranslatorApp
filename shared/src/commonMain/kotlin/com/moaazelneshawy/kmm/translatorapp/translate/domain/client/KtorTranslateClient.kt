package com.moaazelneshawy.kmm.translatorapp.translate.domain.client

import com.moaazelneshawy.kmm.translatorapp.core.domain.Language
import com.moaazelneshawy.kmm.translatorapp.translate.data.TranslateDto
import com.moaazelneshawy.kmm.translatorapp.translate.data.TranslatedDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType

class KtorTranslateClient(
    private val httpClient: HttpClient
) : TranslateClient {
    override suspend fun translate(
        textToTranslate: String,
        fromLanguage: Language,
        toLanguage: Language
    ):String {
        val result = try {
            httpClient.post {
                url(TRANSLATE_URL+"/translate")
                contentType(ContentType.Application.Json)
                setBody {
                    TranslateDto(
                        textToTranslate = textToTranslate,
                        sourceLanguageCode = fromLanguage.langCode,
                        targetLanguageCode = toLanguage.langCode
                    )
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
            throw TranslateException(TranslateError.SERVICE_UNAVAILABLE)
        }

        when (result.status.value) {
            in 200..299 -> Unit
            in 400..499 -> throw TranslateException(TranslateError.CLIENT_ERROR)
            500 -> throw TranslateException(TranslateError.SERVER_ERROR)
            else -> throw TranslateException(TranslateError.UNKNOWN_ERROR)
        }

        return try {
            result.body<TranslatedDto>().translatedText
        } catch(e: Exception) {
            e.printStackTrace()
            throw TranslateException(TranslateError.SERVER_ERROR)
        }
    }

}