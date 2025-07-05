package br.com.aulaandroid.data.networking.impl

import br.com.aulaandroid.data.local.utils.SessionManager
import br.com.aulaandroid.data.networking.SettingsNetworking
import br.com.aulaandroid.util.RequestHandler
import com.google.firebase.auth.FirebaseAuth

class SettingsNetworkingImpl(
    val auth: FirebaseAuth,
    val sessionManager: SessionManager
) : SettingsNetworking {
    override suspend fun logout(): RequestHandler<Unit> {
        return try {
            auth.signOut()
            sessionManager.clearSession()
            RequestHandler.Success(Unit)
        }catch (ex: Exception){
            RequestHandler.Failure(ex)
        }
    }
}