package com.moaazelneshawy.kmm.translatorapp.android.voiceToText.di

import android.app.Application
import com.moaazelneshawy.kmm.translatorapp.android.voiceToText.data.AndroidVoiceToTextListener
import com.moaazelneshawy.kmm.translatorapp.voiceToText.domain.VoiceToTextListener
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object VoiceToTextModule {

    @Provides
    @ViewModelScoped
    fun provideVoiceToTextListener(app: Application): VoiceToTextListener {
        return AndroidVoiceToTextListener(app)
    }
}