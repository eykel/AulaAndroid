package br.com.aulaandroid.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    LaunchedEffect(Unit) {
        viewModel.getUserList()
    }

    val homeState =  viewModel.homeState.collectAsState().value


    when(homeState){
        HomeState.Default -> {
            //TODO
            // Tela inicial que possui informações informando o usuário que ele precisa digitar algo para pesquisar

            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    "ESTAMOS NA HOME",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

            }
        }
        is HomeState.Success -> {
            LazyColumn {
                items(homeState.result.users) { user ->
                    HomeItem(user)
                }
            }
        }
        is HomeState.Failure -> {
            //TODO
            // Caso haja falha, exibe alguma coisa.
        }
    }

}