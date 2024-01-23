package com.moaazelneshawy.kmm.di

import com.moaazelneshawy.kmm.translatorapp.core.httpClient.HttpClientFactory
import com.moaazelneshawy.kmm.translatorapp.database.TranslateDatabase
import com.moaazelneshawy.kmm.translatorapp.translate.data.history.SqlDelightHistoryDataSource
import com.moaazelneshawy.kmm.translatorapp.translate.data.local.DatabaseDriverFactory
import com.moaazelneshawy.kmm.translatorapp.translate.domain.TranslateUseCase
import com.moaazelneshawy.kmm.translatorapp.translate.domain.client.KtorTranslateClient
import com.moaazelneshawy.kmm.translatorapp.translate.domain.client.TranslateClient
import com.moaazelneshawy.kmm.translatorapp.translate.domain.history.HistoryDataSource

class AppModule {
  // this file acts as dependency injection for iOS
  // we will create all dependencies needed as lazy init

  val historyDataSource: HistoryDataSource by lazy {
    SqlDelightHistoryDataSource(
      db = TranslateDatabase(
        driver = DatabaseDriverFactory().create()
      )
    )
  }

  private val httpClient: TranslateClient by lazy {
    KtorTranslateClient(
      httpClient = HttpClientFactory().create()
    )
  }

  val translateUseCase: TranslateUseCase by lazy {
    TranslateUseCase(httpClient, historyDataSource)
  }

}