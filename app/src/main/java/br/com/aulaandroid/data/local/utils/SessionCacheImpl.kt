package br.com.aulaandroid.data.local.utils

import android.content.SharedPreferences
import br.com.aulaandroid.data.model.Session
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import androidx.core.content.edit

class SessionCacheImpl(private val sharedPreferences: SharedPreferences) : SessionCache{

    companion object{
        const val USER_SESSION = "user_session"
    }

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val adapter = moshi.adapter(Session::class.java)

    override fun saveSession(session: Session) {
        sharedPreferences.edit { putString(USER_SESSION, adapter.toJson(session)) }
    }

    override fun getActiveSession(): Session? {
        val json = sharedPreferences.getString(USER_SESSION, null) ?: return null
        return adapter.fromJson(json)
    }

    override fun clearSession() {
        sharedPreferences.edit { remove(USER_SESSION) }
    }
}