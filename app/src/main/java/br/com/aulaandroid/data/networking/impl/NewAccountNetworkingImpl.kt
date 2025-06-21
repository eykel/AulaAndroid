package br.com.aulaandroid.data.networking.impl

import br.com.aulaandroid.data.local.utils.SessionCache
import br.com.aulaandroid.data.model.Session
import br.com.aulaandroid.data.model.UserModel
import br.com.aulaandroid.data.networking.NewAccountNetworking
import br.com.aulaandroid.data.util.Logger
import br.com.aulaandroid.util.RequestHandler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


class NewAccountNetworkingImpl(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val sessionCache: SessionCache
) : NewAccountNetworking {

    private val logger = Logger(TAG)

    override suspend fun newAccount(user: UserModel) : RequestHandler<Unit> {
        return try {
            auth.createUserWithEmailAndPassword(user.email, user.password.orEmpty())
                .await()
                .run {
                    this.user?.uid?.let {
                        createUserOnFireStore(it, user)
                    } ?: RequestHandler.Failure(Exception("Falha ao criar usuário"))
                }
        }catch (ex: Exception){
            logger.logError(NEW_ACCOUNT, ex)
            RequestHandler.Failure(ex)
        }
    }


    private suspend fun createUserOnFireStore(userId: String, user: UserModel) : RequestHandler<Unit>{
        return try {
            firestore
                .collection(USERS_TABLE_FIRESTORE)
                .document(userId)
                .set(user.copy(password = null))
                .await()
                .run {
                    sessionCache.saveSession(Session(user = user, logged = true))
                    RequestHandler.Success(Unit)
                }
        } catch (ex: Exception) {
            logger.logError(CREATE_USER_ON_FIRESTORE, ex)
            RequestHandler.Failure(Exception(ex.cause))
        }
    }


    companion object {
        private const val USERS_TABLE_FIRESTORE = "users"
        private const val TAG = "NewAccountNetworkingImpl"
        private const val NEW_ACCOUNT = "NEW_ACCOUNT"
        private const val CREATE_USER_ON_FIRESTORE = "CREATE_USER_ON_FIRESTORE"
    }

}


