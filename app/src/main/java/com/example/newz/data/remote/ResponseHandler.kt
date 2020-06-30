package com.example.newz.data.remote

import java.io.IOException
import java.lang.Exception

open class ResponseHandler {

    fun <T: Any> handleSuccess(data: T): Result<T> {
        return Result.success(data)
    }

    fun <T: Any> handleError(e: Exception): Result<T> {
        return when (e) {
            is IOException -> Result.error(ErrorType.NetworkError, null)
            else -> Result.error(ErrorType.GenericError(e.message), null)
        }
    }
}