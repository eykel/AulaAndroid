package br.com.aulaandroid.data

interface LoginRepository {
    suspend fun login() : String
}