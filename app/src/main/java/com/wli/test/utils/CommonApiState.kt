package com.wli.test.utils

data class CommonApiState<out T>(val status: Status, val data: T?, val message: String?) {
 
    companion object {
        fun <T> success(data: T?): CommonApiState<T> {
            return CommonApiState(Status.SUCCESS, data, null)
        }
        fun <T> error(msg: String): CommonApiState<T> {
            return CommonApiState(Status.ERROR, null, msg)
        }
        fun <T> loading(): CommonApiState<T> {
            return CommonApiState(Status.LOADING, null, null)
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}