package br.com.aulaandroid.data.repository

import br.com.aulaandroid.util.RequestHandler

interface LoginRepository {
    suspend fun login(email: String, password: String) : RequestHandler<Unit>
}