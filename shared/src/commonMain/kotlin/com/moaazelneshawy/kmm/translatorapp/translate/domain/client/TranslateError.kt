package com.moaazelneshawy.kmm.translatorapp.translate.domain.client

enum class TranslateError {
    SERVICE_UNAVAILABLE,
    CLIENT_ERROR,
    SERVER_ERROR,
    UNKNOWN_ERROR
}

class TranslateException(private val error: TranslateError) : Exception(
    message = "An error occurred when translating: $error"
)