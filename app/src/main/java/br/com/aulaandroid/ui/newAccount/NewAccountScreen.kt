package br.com.aulaandroid.ui.newAccount

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.unit.dp
import br.com.aulaandroid.R
import br.com.aulaandroid.data.model.UserModel
import br.com.aulaandroid.navigation.AulaAndroidState
import br.com.aulaandroid.navigation.Route
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun NewAccountScreen(
    viewModel: NewAccountViewModel,
    onEvent: (AulaAndroidState) -> Unit,
){
    Content(viewModel, onEvent)
}


@Composable
private fun Content(viewModel: NewAccountViewModel, onEvent: (AulaAndroidState) -> Unit,){
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var birthDay by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }

    val validName by viewModel.validName.collectAsState()
    val validEmail by viewModel.validEmail.collectAsState()
    val validPassword by viewModel.validPassword.collectAsState()
    val validBirthDay by viewModel.validBirthDay.collectAsState()
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.loading))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
    val newAccountState by viewModel.newAccountState.collectAsState()

    when(val state = newAccountState){
        is NewAccountState.Failure -> {
            onEvent.invoke(AulaAndroidState.Error(state.ex.message.orEmpty()))
        }
        is NewAccountState.Loading -> {
            loading = state.isLoading
        }
        NewAccountState.Success -> {
            onEvent.invoke(AulaAndroidState.Navigate(Route.LoginScreen))
        }

        NewAccountState.Default -> {}
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextField(
            modifier = Modifier.padding(20.dp),
            value = name,
            onValueChange = { newValue ->
                name = newValue
                viewModel.validName(name)
            },
            label = { Text(text = "Nome") },
            isError = !validName,
            maxLines = 1,
            placeholder = { Text(text = "Nome") },
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Blue,
            )
        )

        TextField(
            modifier = Modifier.padding(20.dp),
            value = email,
            onValueChange = { newValue ->
                email = newValue
                viewModel.validEmail(email)
            },
            label = { Text(text = "Email") },
            isError = !validEmail,
            maxLines = 1,
            placeholder = { Text(text = "Email") },
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Blue,
            )
        )

        TextField(
            modifier = Modifier.padding(20.dp),
            value = birthDay,
            onValueChange = { newValue ->
                birthDay = newValue
                viewModel.validBirthDay(birthDay)
            },
            maxLines = 1,
            label = { Text(text = "Data de Nascimento") },
            isError = !validBirthDay,
            placeholder = { Text(text = "Data de Nascimento") },
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Blue,
            )
        )

        TextField(
            modifier = Modifier.padding(20.dp),
            value = password,
            onValueChange = { newValue ->
                password = newValue
                viewModel.validPassword(password)
            },
            maxLines = 1,
            label = { Text(text = "Senha") },
            isError = !validPassword,
            placeholder = { Text(text = "Senha") },
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Blue,
            )
        )

        Button(
            modifier = Modifier.padding(10.dp).widthIn(min = 150.dp),
            onClick = {
                viewModel.createNewAccount(
                    UserModel(
                        name = name,
                        birthDay = birthDay,
                        password = password,
                        email = email
                    )
                )
            },
        ) {
            if (loading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(ButtonDefaults.IconSize),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text = "Registrar",
                )
            }
        }
    }
}