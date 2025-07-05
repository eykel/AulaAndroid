package br.com.aulaandroid.data.repository.impl

import br.com.aulaandroid.data.local.utils.SessionManager
import br.com.aulaandroid.data.model.Session
import br.com.aulaandroid.data.model.UserModel
import br.com.aulaandroid.data.repository.LoginRepository
import br.com.aulaandroid.data.networking.LoginNetworking
import br.com.aulaandroid.util.RequestHandler

class LoginRepositoryImpl(
    private val networking: LoginNetworking,
    private val sessionManager: SessionManager
) : LoginRepository {
    override suspend fun login(email: String, password: String): RequestHandler<Unit>  {
        val result = networking.login(email, password)
        when(result){
            is RequestHandler.Success -> {
                //save user session just with email.
                sessionManager.saveSession(
                    Session(
                        UserModel.getUserModel(email = email, id = result.content),
                        logged = true
                    )
                )
                return RequestHandler.Success(Unit)
            }
            is RequestHandler.Failure -> {
                return RequestHandler.Failure(result.ex)
            }
        }
    }
}