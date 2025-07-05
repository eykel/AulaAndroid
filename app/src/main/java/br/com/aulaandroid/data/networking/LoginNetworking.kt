package br.com.aulaandroid.data.networking

import br.com.aulaandroid.util.RequestHandler


interface LoginNetworking  {
    suspend fun login(email: String, password: String) : RequestHandler<String>
}