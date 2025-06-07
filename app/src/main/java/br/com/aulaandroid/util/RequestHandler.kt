package br.com.aulaandroid.util

sealed class RequestHandler<out T> {
    data class Success<out T>(val content: T) : RequestHandler<T>()
    data class Failure(val ex: Exception) :  RequestHandler<Nothing>()
}