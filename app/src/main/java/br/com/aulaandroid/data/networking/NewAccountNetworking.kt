package br.com.aulaandroid.data.networking

import br.com.aulaandroid.data.model.UserModel
import br.com.aulaandroid.data.model.UserService
import br.com.aulaandroid.util.RequestHandler

interface NewAccountNetworking {

    suspend fun newAccount(user: UserService) : RequestHandler<UserService>
}