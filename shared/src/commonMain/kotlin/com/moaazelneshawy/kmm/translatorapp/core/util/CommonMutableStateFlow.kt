package com.moaazelneshawy.kmm.translatorapp.core.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

expect class CommonMutableStateFlow<T>(flow: MutableStateFlow<T>) : MutableStateFlow<T>

fun <T> MutableStateFlow<T>.CommonMutableStateFlow() = CommonMutableStateFlow(this)