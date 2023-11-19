package com.moaazelneshawy.kmm.translatorapp.translate.data

import com.moaazelneshawy.kmm.translatorapp.core.domain.Language
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TranslateDto(
    @SerialName("q") val textToTranslate: String,
    @SerialName("source") val fromLanguageCode: String,
    @SerialName("target") val toLanguageCode: String
)