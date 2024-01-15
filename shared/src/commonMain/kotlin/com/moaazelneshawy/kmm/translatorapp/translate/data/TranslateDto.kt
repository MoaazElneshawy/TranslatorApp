package com.moaazelneshawy.kmm.translatorapp.translate.data

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class TranslateDto(
    @SerialName("q") val textToTranslate: String,
    @SerialName("source") val sourceLanguageCode: String,
    @SerialName("target") val targetLanguageCode: String
)