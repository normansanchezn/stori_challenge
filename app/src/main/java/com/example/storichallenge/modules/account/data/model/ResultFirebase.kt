package com.example.storichallenge.modules.account.data.model

sealed class ResultFirebase<T>(
    val data: T? = null,
    val message: String? = null
){
    class Success<T>(data: T) : ResultFirebase<T>(data = data)
    class Error<T>(message: String, data: T) : ResultFirebase<T>(message = message, data = data)
}