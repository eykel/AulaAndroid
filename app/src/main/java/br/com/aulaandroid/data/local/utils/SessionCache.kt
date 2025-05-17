package br.com.aulaandroid.data.local.utils

import br.com.aulaandroid.data.model.Session

interface SessionCache {

    fun saveSession(session: Session)

    fun getActiveSession() : Session?

    fun clearSession()
}