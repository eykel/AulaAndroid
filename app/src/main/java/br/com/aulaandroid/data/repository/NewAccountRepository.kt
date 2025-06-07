package br.com.aulaandroid.data.repository

import br.com.aulaandroid.data.model.UserModel
import br.com.aulaandroid.util.RequestHandler

interface NewAccountRepository {

    suspend fun newAccount(user: UserModel) : RequestHandler<Unit>
}