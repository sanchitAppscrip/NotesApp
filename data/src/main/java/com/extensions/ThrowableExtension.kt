package com.extensions

import com.error.AppError

fun Throwable.toFileFailure(): AppError {
    return AppError.FileFailure(this)
}

