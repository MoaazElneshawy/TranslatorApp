package com.moaazelneshawy.kmm.translatorapp.translate.data.history

import com.moaazelneshawy.kmm.translatorapp.core.util.CommonFlow
import com.moaazelneshawy.kmm.translatorapp.core.util.toCommonFlow
import com.moaazelneshawy.kmm.translatorapp.database.TranslateDatabase
import com.moaazelneshawy.kmm.translatorapp.translate.domain.history.HistoryDataSource
import com.moaazelneshawy.kmm.translatorapp.translate.domain.history.HistoryItem
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.map

class SqlDelightHistoryDataSource(
    db: TranslateDatabase
) : HistoryDataSource {

    private val queries = db.translateQueries

    override fun getHistory(): CommonFlow<List<HistoryItem>> {
        return queries
            .getHistory()
            .asFlow()
            .mapToList()
            .map { entities ->
                entities.map { entity ->
                    entity.toHistoryItem()
                }
            }
            .toCommonFlow()
    }

    override suspend fun insertHistory(historyItem: HistoryItem) {
        queries.insertHistoryEntity(
            historyItem.id,
            historyItem.fromLanguageCode,
            historyItem.fromText,
            historyItem.toLanguageCode,
            historyItem.toText,
            historyItem.timestamp
        )
    }
}