package br.com.aulaandroid.data.networking.impl

import android.content.Context
import br.com.aulaandroid.R
import br.com.aulaandroid.data.networking.LoginNetworking
import br.com.aulaandroid.data.util.Logger
import br.com.aulaandroid.util.RequestHandler
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class LoginNetworkingImpl(
    private val auth: FirebaseAuth,
    private val context: Context
) : LoginNetworking {

    private val logger = Logger(LAYER_CLASS)

    override suspend fun login(email: String, password: String) : RequestHandler<String> {
        return try {
            auth.signInWithEmailAndPassword(email, password)
                .await()
                .run {
                    this.user?.uid?.let {
                        logger.logSuccess(LOGIN, it.toString())
                        RequestHandler.Success(it)
                    } ?: RequestHandler.Failure(Exception(context.getString(R.string.failed_to_log_in)))
                }
        }catch (ex: Exception){
            logger.logError(LOGIN, ex)
            RequestHandler.Failure(ex)
        }
    }

    companion object {
        private const val LAYER_CLASS = "LoginNetworkingImpl"
        private const val LOGIN = "LOGIN"
    }
}