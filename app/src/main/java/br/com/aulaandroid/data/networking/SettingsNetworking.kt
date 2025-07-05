package br.com.aulaandroid.data.networking

import br.com.aulaandroid.util.RequestHandler

interface SettingsNetworking {
    suspend fun logout() : RequestHandler<Unit>
}