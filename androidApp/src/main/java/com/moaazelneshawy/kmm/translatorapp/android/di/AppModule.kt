package com.moaazelneshawy.kmm.translatorapp.android.di

import android.app.Application
import com.moaazelneshawy.kmm.translatorapp.core.httpClient.HttpClientFactory
import com.moaazelneshawy.kmm.translatorapp.database.TranslateDatabase
import com.moaazelneshawy.kmm.translatorapp.translate.data.history.SqlDelightHistoryDataSource
import com.moaazelneshawy.kmm.translatorapp.translate.data.local.DatabaseDriverFactory
import com.moaazelneshawy.kmm.translatorapp.translate.domain.TranslateUseCase
import com.moaazelneshawy.kmm.translatorapp.translate.domain.client.KtorTranslateClient
import com.moaazelneshawy.kmm.translatorapp.translate.domain.client.TranslateClient
import com.moaazelneshawy.kmm.translatorapp.translate.domain.history.HistoryDataSource
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesTranslateUseCase(
        httpClient: TranslateClient,
        dataSource: HistoryDataSource
    ): TranslateUseCase {
        return TranslateUseCase(httpClient, dataSource)
    }

    @Provides
    @Singleton
    fun providesTranslateClient(httpClient: HttpClient): TranslateClient {
        return KtorTranslateClient(httpClient)
    }

    @Provides
    @Singleton
    fun providesHttpClient(): HttpClient {
        return HttpClientFactory().create()
    }

    @Provides
    @Singleton
    fun providesHistoryDataSource(db: TranslateDatabase): HistoryDataSource {
        return SqlDelightHistoryDataSource(db)
    }

    @Provides
    @Singleton
    fun providesTranslateDatabase(sqlDriver: SqlDriver): TranslateDatabase {
        return TranslateDatabase(sqlDriver)
    }

    @Provides
    @Singleton
    fun providesDatabaseDriver(application: Application): SqlDriver {
        return DatabaseDriverFactory(application).create()
    }

}