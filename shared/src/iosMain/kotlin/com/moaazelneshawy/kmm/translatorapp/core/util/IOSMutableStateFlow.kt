package com.moaazelneshawy.kmm.translatorapp.core.util

import kotlinx.coroutines.flow.MutableStateFlow

class IOSMutableStateFlow<T>(initialValue: T)
  : CommonMutableStateFlow<T>(MutableStateFlow(initialValue))