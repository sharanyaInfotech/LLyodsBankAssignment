package com.example.llyodsbankassignment.utils

/*
sealed class UIState {
     class LOADING(val data: Any?) : UIState()
     class ERROR(val e: String?) : UIState()
     class SUCCESS(val data: Any?) : UIState()
}
*/

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {

    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }
}
