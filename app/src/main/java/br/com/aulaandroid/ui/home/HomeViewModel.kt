package br.com.aulaandroid.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aulaandroid.data.repository.GithubRepository
import br.com.aulaandroid.util.RequestHandler
import kotlinx.coroutines.launch

class HomeViewModel(val repository: GithubRepository) : ViewModel() {

    fun getUserList(){
        viewModelScope.launch {
            when(val result = repository.getUserList("eykel")){
                is RequestHandler.Failure -> TODO()
                is RequestHandler.Success -> TODO()
            }
        }
    }
}