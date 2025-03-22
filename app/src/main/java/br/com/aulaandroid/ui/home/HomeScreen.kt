package br.com.aulaandroid.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import br.com.aulaandroid.R
import br.com.aulaandroid.navigation.AulaAndroidState
import br.com.aulaandroid.navigation.Route
import br.com.aulaandroid.ui.components.GenericError
import br.com.aulaandroid.ui.theme.MyBlue


@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onEvent: (AulaAndroidState) -> Unit
) {
    Content(viewModel, onEvent)
}

@Composable
private fun Content(viewModel: HomeViewModel, onEvent: (AulaAndroidState) -> Unit){
    var search by remember { mutableStateOf("") }
    val homeState =  viewModel.homeState.collectAsState().value

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 8.dp),
            shape = RoundedCornerShape(8.dp),
            value = search,
            onValueChange = { newValue ->
                search = newValue
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                viewModel.getUserList(search)

            }),
            trailingIcon = {
                IconButton(
                    onClick = { viewModel.getUserList(search) }
                ) {
                    Icon(Icons.AutoMirrored.Filled.Send, "", tint = MyBlue)
                }
            },
            placeholder = { Text(text = stringResource(R.string.search_placeholder)) },
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Blue,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )


        when(homeState){
            HomeState.Default -> {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        stringResource(R.string.begin_search_user),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Icon(
                        Icons.Outlined.Info,
                        "",
                        tint = MyBlue,
                        modifier = Modifier.size(60.dp)

                    )

                }
            }
            is HomeState.Success -> {
                LazyColumn {
                    items(homeState.result.users) { user ->
                        HomeItem(user){
                            onEvent.invoke(AulaAndroidState.Navigate(Route.DetailScreen(it)))
                        }
                    }
                }
            }
            is HomeState.Failure -> {
                GenericError()
                onEvent.invoke(AulaAndroidState.Error(homeState.ex.message.orEmpty()))
            }
        }
    }
}