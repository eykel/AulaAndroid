package br.com.aulaandroid.util

sealed class RequestHandler {
    data class Success(val result: Any) : RequestHandler()
    data class Failure(val ex: Exception) : RequestHandler()
}