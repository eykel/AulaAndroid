package br.com.aulaandroid.data.networking

import br.com.aulaandroid.util.RequestHandler

interface NewAccountNetworking {

    suspend fun newAccount(email: String, password: String) : RequestHandler
}