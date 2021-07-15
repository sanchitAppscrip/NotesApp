package com.error

sealed class AppError {
    data class ApiError(val statusCode: Int, val message: String) : AppError()
    data class FileFailure(val throwable: Throwable) : AppError()
}