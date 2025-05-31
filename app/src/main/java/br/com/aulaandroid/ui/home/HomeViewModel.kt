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


    fun getUserList(param: String){
        viewModelScope.launch {
            if(param.isNotEmpty()){
                when(val result = repository.getUserList(param)){
                    is RequestHandler.Failure -> {
                        Log.d("TAG", "getUserList: ${result.ex}")
                        _homeState.value = HomeState.Failure(result.ex)
                    }
                    is RequestHandler.Success -> {
                        Log.d("TAG", "getUserList: ${result.content}")
                        _homeState.value = HomeState.Success(result.content as GithubUserList)
                    }
                }
            }else{
                _homeState.value = HomeState.Default
            }
        }
    }
}