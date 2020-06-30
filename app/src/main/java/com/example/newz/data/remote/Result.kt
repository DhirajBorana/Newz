package com.example.newz.data.remote

import com.example.newz.data.remote.Status.*

data class Result<out T>(val status: Status, val data: T?, val error: ErrorType?) {

    companion object {
        fun <T> success(data: T?): Result<T> {
            return Result(SUCCESS, data, null)
        }

        fun <T> error(error: ErrorType, data: T?): Result<T> {
            return Result(ERROR, data, error)
        }

        fun <T> loading(data: T?): Result<T> {
            return Result(LOADING, data, null)
        }
    }
}

enum class Status {
    SUCCESS, LOADING, ERROR
}

sealed class ErrorType {
    object NetworkError : ErrorType()
    data class GenericError(val msg: String?) : ErrorType()
}