package com.moaazelneshawy.kmm.translatorapp.android.translate.presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moaazelneshawy.kmm.translatorapp.translate.domain.TranslateUseCase
import com.moaazelneshawy.kmm.translatorapp.translate.domain.history.HistoryDataSource
import com.moaazelneshawy.kmm.translatorapp.translate.presentation.TranslateEvent
import com.moaazelneshawy.kmm.translatorapp.translate.presentation.TranslateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidTranslateViewModel @Inject constructor(
    private val translateUseCase: TranslateUseCase,
    private val historyDataSource: HistoryDataSource
) : ViewModel() {

    private val translateViewModel by lazy {
        TranslateViewModel(translateUseCase, historyDataSource, viewModelScope)
    }

    val state = translateViewModel.state

    fun onEvent(event: TranslateEvent) {
        translateViewModel.onEvent(event)
    }
}