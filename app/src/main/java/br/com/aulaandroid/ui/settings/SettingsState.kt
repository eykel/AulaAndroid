package br.com.aulaandroid.ui.settings


sealed class SettingsState {
    object Default: SettingsState()
    object Success: SettingsState()
    data class Failure(val ex: Exception) : SettingsState()
}