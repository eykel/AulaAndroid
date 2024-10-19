package br.com.aulaandroid.data.networking.impl

import br.com.aulaandroid.data.networking.NewAccountNetworking
import br.com.aulaandroid.util.RequestHandler
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await


class NewAccountNetworkingImpl(private val auth: FirebaseAuth) : NewAccountNetworking {
    override suspend fun newAccount(email: String, password: String) : RequestHandler {
        return try {
            auth.createUserWithEmailAndPassword(email, password)
                .await()
                .run {
                    this.user?.uid?.let {
                        RequestHandler.Success("")
                    }
                } ?: RequestHandler.Failure(Exception("Falha ao Logar"))
        }catch (ex: Exception){
            RequestHandler.Failure(ex)
        }
    }


    fun createUserOnFireStore(){

    }
}


