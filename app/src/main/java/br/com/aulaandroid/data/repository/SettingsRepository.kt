package br.com.aulaandroid.data.repository

import br.com.aulaandroid.util.RequestHandler

interface SettingsRepository {

    suspend fun logout() : RequestHandler<Unit>
}