package com.moaazelneshawy.kmm.translatorapp.translate.domain.history

import com.moaazelneshawy.kmm.translatorapp.core.util.CommonFlow

interface HistoryDataSource {

    fun getHistory(): CommonFlow<List<HistoryItem>>

    suspend fun insertHistory(historyItem: HistoryItem)

}