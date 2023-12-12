package com.moaazelneshawy.kmm.translatorapp.core.util

sealed class Resource<T>(val data: T?, val ex: Exception? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(ex: Exception) : Resource<T>(null, ex)
}
