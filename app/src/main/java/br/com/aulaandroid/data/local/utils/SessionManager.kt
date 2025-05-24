package br.com.aulaandroid.data.local.utils

import br.com.aulaandroid.data.model.Session
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SessionManager(private val sessionCache: SessionCache) {

    private val _session = MutableStateFlow<Session?>(sessionCache.getActiveSession())
    val session: StateFlow<Session?> = _session.asStateFlow()

    fun saveSession(session: Session) {
        sessionCache.saveSession(session)
        _session.value = session
    }

    fun clearSession() {
        sessionCache.clearSession()
        _session.value = null
    }
}