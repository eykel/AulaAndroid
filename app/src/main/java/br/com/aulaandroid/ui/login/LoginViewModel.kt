package br.com.aulaandroid.ui.login

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aulaandroid.data.LoginRepository
import br.com.aulaandroid.util.RequestHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {

    private val _validEmail  = MutableStateFlow(true)
    val validEmail = _validEmail

    private val _loadingButton = MutableStateFlow(false)
    val loadingButton = _loadingButton.asStateFlow()

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Default)
    val loginState = _loginState.asStateFlow()

    fun validEmail(email: String){
        _validEmail.value = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun login(email: String, password: String){
        viewModelScope.launch {
            _loadingButton.value = true
            when(val result = repository.login(email, password)){
                is RequestHandler.Failure -> {
                    _loadingButton.value = false
                    _loginState.value = LoginState.Failure(result.ex)
                }
                is RequestHandler.Success -> {
                    _loadingButton.value = false
                    _loginState.value = LoginState.Success
                }
            }
        }
    }
}