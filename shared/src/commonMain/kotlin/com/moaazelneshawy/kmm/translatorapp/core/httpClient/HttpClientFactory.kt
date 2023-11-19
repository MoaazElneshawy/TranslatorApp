package com.moaazelneshawy.kmm.translatorapp.core.httpClient

import io.ktor.client.HttpClient

expect class HttpClientFactory {
    fun create(): HttpClient
}