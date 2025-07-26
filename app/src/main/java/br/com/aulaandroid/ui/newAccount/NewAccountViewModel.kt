package br.com.aulaandroid.ui.newAccount

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aulaandroid.data.repository.NewAccountRepository
import br.com.aulaandroid.data.model.UserModel
import br.com.aulaandroid.util.RequestHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class NewAccountViewModel(
    private val repository: NewAccountRepository
) : ViewModel() {

    private val _validEmail  = MutableStateFlow(true)
    val validEmail : StateFlow<Boolean> = _validEmail

    private val  _validPassword = MutableStateFlow(true)
    val validPassword: StateFlow<Boolean> = _validPassword

    private val _validName = MutableStateFlow(true)
    val validName : StateFlow<Boolean> = _validName

    private val _validBirthDay = MutableStateFlow(true)
    val validBirthDay : StateFlow<Boolean> = _validBirthDay

    private val _newAccountState = MutableStateFlow<NewAccountState>(NewAccountState.Default)
    val newAccountState : StateFlow<NewAccountState> = _newAccountState.asStateFlow()

    private val _loadingButton = MutableStateFlow(false)
    val loadingButton = _loadingButton.asStateFlow()


    fun createNewAccount(user: UserModel){

        viewModelScope.launch {
            _loadingButton.value = true
            when(val result = repository.newAccount(user)){
                is RequestHandler.Success -> {
                    _loadingButton.value = false
                    _newAccountState.value = NewAccountState.Success
                }
                is RequestHandler.Failure -> {
                    _loadingButton.value = false
                    _newAccountState.value = NewAccountState.Failure(result.ex)
                }
            }
        }
    }

    fun validName(name: String){
        _validName.value = name.split(" ").size > 1
    }

    fun validEmail(email: String){
        _validEmail.value = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun validPassword(password: String){
        _validPassword.value = password.length > 5
                && password.any { it.isDigit() }
                && password.any { it.isLetter() }
                && password.any { it.isUpperCase() }
    }

    fun validBirthDay(date: String){
        try {
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val localDate = LocalDate.parse(date, formatter)
            _validBirthDay.value =  !(!formatter.format(localDate).equals(date) || localDate > LocalDate.now())
        } catch (e: Exception) {
            _validBirthDay.value =  false
        }
    }
}