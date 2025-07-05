package br.com.aulaandroid.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aulaandroid.data.repository.SettingsRepository
import br.com.aulaandroid.util.RequestHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(val repository: SettingsRepository): ViewModel() {

    private val _settingsState = MutableStateFlow<SettingsState>(SettingsState.Default)
    val settingsState = _settingsState.asStateFlow()

    fun logout(){
        viewModelScope.launch {
            when(val result = repository.logout()){
                is RequestHandler.Failure -> {
                    _settingsState.value = SettingsState.Failure(result.ex)
                }
                is RequestHandler.Success -> {
                    _settingsState.value = SettingsState.Success
                }
            }
        }
    }
}