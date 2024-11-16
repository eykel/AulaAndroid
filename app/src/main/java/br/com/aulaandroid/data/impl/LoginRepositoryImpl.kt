package br.com.aulaandroid.data.impl

import br.com.aulaandroid.data.LoginRepository
import br.com.aulaandroid.data.networking.LoginNetworking
import br.com.aulaandroid.util.RequestHandler

class LoginRepositoryImpl(private val networking: LoginNetworking) : LoginRepository {
    override suspend fun login(email: String, password: String): RequestHandler =
        networking.login(email, password)

}