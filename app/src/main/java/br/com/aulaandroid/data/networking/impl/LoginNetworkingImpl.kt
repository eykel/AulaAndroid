package br.com.aulaandroid.data.networking.impl

import br.com.aulaandroid.data.local.utils.SessionManager
import br.com.aulaandroid.data.model.Session
import br.com.aulaandroid.data.model.UserModel
import br.com.aulaandroid.data.networking.LoginNetworking
import br.com.aulaandroid.data.util.Logger
import br.com.aulaandroid.util.RequestHandler
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class LoginNetworkingImpl(
    private val auth: FirebaseAuth,
    private val sessionManager: SessionManager
) : LoginNetworking {

    private val logger = Logger(TAG)

    override suspend fun login(email: String, password: String) : RequestHandler<Unit> {
        return try {
            auth.signInWithEmailAndPassword(email, password)
                .await()
                .run {
                    this.user?.uid?.let {
                        //save user session just with email.
                        sessionManager.saveSession(
                            Session(
                                UserModel.getUserModel(email = email),
                                logged = true
                            )
                        )
                        RequestHandler.Success(Unit)
                    } ?: RequestHandler.Failure(Exception("Falha ao logar"))
                }
        }catch (ex: Exception){
            logger.logError(LOGIN, ex)
            RequestHandler.Failure(ex)
        }
    }

    companion object {
        private const val TAG = "LoginNetworkingImpl"
        private const val LOGIN = "LOGIN"
    }
}