package br.com.aulaandroid.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aulaandroid.data.model.GithubUserList
import br.com.aulaandroid.data.repository.GithubRepository
import br.com.aulaandroid.util.RequestHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(val repository: GithubRepository) : ViewModel() {

    private val _homeState = MutableStateFlow<HomeState>(HomeState.Default)
    val homeState = _homeState.asStateFlow()


    fun getUserList(){
        viewModelScope.launch {
            when(val result = repository.getUserList("eykel")){
                is RequestHandler.Failure -> {
                    Log.d("TAG", "getUserList: ${result.ex}")
                }
                is RequestHandler.Success -> {
                    Log.d("TAG", "getUserList: ${result.result}")
                    _homeState.value = HomeState.Success(result.result as GithubUserList)
                }
            }
        }
    }
}