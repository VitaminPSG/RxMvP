package com.android.rxmvp.domain.models

sealed class SafeResult<out R> {
    data class Success<out T>(val data: T) : SafeResult<T>()
    data class Error(val error: Throwable) : SafeResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[$data]"
            is Error -> "Error[$error]"
        }
    }

    fun isSuccess(): Boolean {
        return this is Success
    }

    companion object {

        fun <T> success(value: T): SafeResult<T> {
            return SafeResult.Success<T>(value)
        }

        fun error(exception: Throwable): SafeResult<Nothing> {
            return SafeResult.Error(exception)
        }
    }
}