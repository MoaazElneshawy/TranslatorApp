package com.moaazelneshawy.kmm.translatorapp.translate.data.history

import com.moaazelneshawy.kmm.translatorapp.translate.domain.history.HistoryItem
import database.HistoryEntity

fun HistoryItem.toHistoryEntity(): HistoryEntity {
    return HistoryEntity(
        id,
        fromLanguageCode,
        fromText,
        toLanguageCode,
        toText,
        timestamp
    )

}

fun HistoryEntity.toHistoryItem(): HistoryItem {
    return HistoryItem(
        id,
        fromLanguageCode,
        fromText,
        toLanguageCode,
        toText,
        timestamp
    )
}