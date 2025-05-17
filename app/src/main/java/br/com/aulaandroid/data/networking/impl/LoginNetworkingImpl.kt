package br.com.aulaandroid.data.networking.impl

import br.com.aulaandroid.data.networking.LoginNetworking
import br.com.aulaandroid.util.RequestHandler
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class LoginNetworkingImpl(private val auth: FirebaseAuth) : LoginNetworking {
    override suspend fun login(email: String, password: String) : RequestHandler {
        return try {
            auth.signInWithEmailAndPassword(email, password)
                .await()
                .run {
                    this.user?.uid?.let {
                        RequestHandler.Success(Unit)
                    } ?: RequestHandler.Failure(Exception("Falha ao logar"))
                }
        }catch (ex: Exception){
            RequestHandler.Failure(ex)
        }
    }
}