package br.com.aulaandroid.data.networking.impl

import br.com.aulaandroid.data.model.UserModel
import br.com.aulaandroid.data.networking.NewAccountNetworking
import br.com.aulaandroid.util.RequestHandler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


class NewAccountNetworkingImpl(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : NewAccountNetworking {
    override suspend fun newAccount(user: UserModel) : RequestHandler {
        return try {
            auth.createUserWithEmailAndPassword(user.email, user.password.orEmpty())
                .await()
                .run {
                    this.user?.uid?.let {
                        createUserOnFireStore(it, user)
                    } ?: RequestHandler.Failure(Exception("Falha ao criar usuário"))
                }
        }catch (ex: Exception){
            RequestHandler.Failure(ex)
        }
    }


    private suspend fun createUserOnFireStore(userId: String, user: UserModel) : RequestHandler{
        return try {
            firestore
                .collection(USERS_TABLE_FIRESTORE)
                .document(userId)
                .set(user.copy(password = null))
                .await()
                .run {
                    RequestHandler.Success(Unit)
                }
        } catch (ex: Exception) {
            RequestHandler.Failure(Exception(ex.cause))
        }
    }


    companion object {
        const val USERS_TABLE_FIRESTORE = "users"
    }

}


