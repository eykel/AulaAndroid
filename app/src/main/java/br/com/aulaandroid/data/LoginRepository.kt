package br.com.aulaandroid.data

import br.com.aulaandroid.util.RequestHandler

interface LoginRepository {
    suspend fun login(email: String, password: String) : RequestHandler
}