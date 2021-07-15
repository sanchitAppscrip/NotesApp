package com.model

sealed class Status {
    object LOADING : Status()
    object SUCCESS : Status()
    object ERROR : Status()
}