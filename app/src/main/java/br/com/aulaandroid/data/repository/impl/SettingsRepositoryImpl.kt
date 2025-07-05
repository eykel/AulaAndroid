package br.com.aulaandroid.data.repository.impl

import br.com.aulaandroid.data.networking.SettingsNetworking
import br.com.aulaandroid.data.repository.SettingsRepository
import br.com.aulaandroid.util.RequestHandler

class SettingsRepositoryImpl(private val networking: SettingsNetworking) : SettingsRepository {
    override suspend fun logout(): RequestHandler<Unit> = networking.logout()
}