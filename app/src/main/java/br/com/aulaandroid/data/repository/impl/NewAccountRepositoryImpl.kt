package br.com.aulaandroid.data.repository.impl

import br.com.aulaandroid.data.local.utils.SessionManager
import br.com.aulaandroid.data.mappers.toUserModel
import br.com.aulaandroid.data.mappers.toUserService
import br.com.aulaandroid.data.model.Session
import br.com.aulaandroid.data.repository.NewAccountRepository
import br.com.aulaandroid.data.model.UserModel
import br.com.aulaandroid.data.networking.NewAccountNetworking
import br.com.aulaandroid.util.RequestHandler

class NewAccountRepositoryImpl(
    private val networking: NewAccountNetworking,
    private val sessionManager: SessionManager
) : NewAccountRepository {

    override suspend fun newAccount(user: UserModel) : RequestHandler<Unit> {
        val result = networking.newAccount(user.toUserService())
        return when(result){
            is RequestHandler.Success -> {
                sessionManager.saveSession(Session(user = result.content.toUserModel(), logged = true))
                RequestHandler.Success(Unit)
            }

            is RequestHandler.Failure -> {
                RequestHandler.Failure(result.ex)
            }
        }
    }

}