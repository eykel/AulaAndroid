package br.com.aulaandroid.data.networking.impl

import android.content.Context
import br.com.aulaandroid.R
import br.com.aulaandroid.data.model.UserService
import br.com.aulaandroid.data.networking.NewAccountNetworking
import br.com.aulaandroid.data.util.Logger
import br.com.aulaandroid.util.RequestHandler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


class NewAccountNetworkingImpl(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val context: Context
) : NewAccountNetworking {

    private val logger = Logger(TAG)

    override suspend fun newAccount(user: UserService) : RequestHandler<UserService> {
        return try {
            auth.createUserWithEmailAndPassword(user.email, user.password.orEmpty())
                .await()
                .run {
                    this.user?.uid?.let {
                        logger.logSuccess(NEW_ACCOUNT, it.toString())
                        createUserOnFireStore(it, user)
                    } ?: RequestHandler.Failure(Exception(context.getString(R.string.failed_to_create_user)))
                }
        }catch (ex: Exception){
            logger.logError(NEW_ACCOUNT, ex)
            RequestHandler.Failure(ex)
        }
    }


    private suspend fun createUserOnFireStore(userId: String, user: UserService) : RequestHandler<UserService>{
        return try {
            firestore
                .collection(USERS_TABLE_FIRESTORE)
                .document(userId)
                .set(user.copy(password = null))
                .await()
                .run {
                    logger.logSuccess(CREATE_USER_ON_FIRESTORE, userId)
                    RequestHandler.Success(user.copy(id = userId))
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


