package com.easynvest.investments

sealed class ViewState<out T> {
    object Loading : ViewState<Nothing>()
    data class Failure<out T>(val error: Exception) : ViewState<T>()
    data class Success<out T>(val result: T) : ViewState<T>()
}
