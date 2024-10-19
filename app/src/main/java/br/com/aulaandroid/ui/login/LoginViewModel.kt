package br.com.aulaandroid.ui.login

import android.util.Patterns
import androidx.lifecycle.ViewModel
import br.com.aulaandroid.data.LoginRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {

    private val _validEmail  = MutableStateFlow<Boolean>(true)
    val validEmail : StateFlow<Boolean> = _validEmail

    private val _register = MutableStateFlow<Boolean>(false)
    val register : StateFlow<Boolean> = _register

    fun validEmail(email: String){
        _validEmail.value = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}