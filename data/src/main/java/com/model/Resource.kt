package com.model

import com.error.AppError


data class Resource<T>(val status: Status, var data: T? = null, val error: AppError? = null) {
    companion object {
        fun <T> success(data: T? = null): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(error: AppError?, data: T? = null): Resource<T> {
            return Resource(Status.ERROR, data, error)
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }

    fun isSuccess(): Boolean = status == Status.SUCCESS

    fun isLoading(): Boolean = status == Status.LOADING

}