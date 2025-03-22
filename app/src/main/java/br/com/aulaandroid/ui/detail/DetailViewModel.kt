package br.com.aulaandroid.ui.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aulaandroid.data.model.UserDetailModel
import br.com.aulaandroid.data.repository.GithubRepository
import br.com.aulaandroid.util.RequestHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(val repository: GithubRepository) : ViewModel() {

    private val _detailState = MutableStateFlow<DetailState>(DetailState.Default)
    val detailState = _detailState.asStateFlow()

    fun getUserDetail(login: String){
        viewModelScope.launch {
            when (val result = repository.getUserDetail(login)) {
                is RequestHandler.Failure -> {
                    Log.d("PASSEI AQUI", "getUserDetail: ${result.ex}")
                    _detailState.value = DetailState.Failure(result.ex)
                }

                is RequestHandler.Success -> {
                    Log.d("PASSEI AQUI", "getUserDetail: ${result.result}")
                    _detailState.value = DetailState.Success(result.result as UserDetailModel)
                }
            }
        }
    }
}