package br.com.aulaandroid.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.aulaandroid.R
import br.com.aulaandroid.navigation.AulaAndroidState
import br.com.aulaandroid.navigation.Route
import br.com.aulaandroid.ui.components.ButtonCustom
import br.com.aulaandroid.ui.components.TextFieldCustom
import br.com.aulaandroid.ui.components.util.TextFieldType
import br.com.aulaandroid.ui.theme.MyBlue

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onEvent: (AulaAndroidState) -> Unit,
) {
    Content(viewModel, onEvent)
}

@Composable
private fun Content(viewModel: LoginViewModel, onEvent: (AulaAndroidState) -> Unit){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val validEmail by viewModel.validEmail.collectAsState()
    val loading by viewModel.loadingButton.collectAsStateWithLifecycle()
    val loginState by viewModel.loginState.collectAsState()


    LaunchedEffect(loginState) {
        when(val state = loginState){
            is LoginState.Failure -> {
                onEvent.invoke(AulaAndroidState.Error(state.ex.message.orEmpty()))
            }
            LoginState.Success -> {
                onEvent.invoke(AulaAndroidState.Navigate(Route.HomeScreen))
            }
            LoginState.Default -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        Text(
            text = stringResource(R.string.app_name),
            Modifier.padding(20.dp),
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight(600),
                color = MyBlue
            )
        )

        TextFieldCustom(
            textValue = email,
            onValueChange = { newValue ->
                email = newValue
                viewModel.validEmail(email)
            },
            labelText = R.string.email_text,
            isError = !validEmail,
        )


        TextFieldCustom(
            textValue = password,
            onValueChange = { newValuer ->
                password = newValuer
            },
            labelText = R.string.password_text,
            type = TextFieldType.Password
        )

        ButtonCustom(
            onClick = {
                viewModel.login(email, password)
            },
            text = R.string.login_text_button,
            loading = loading
        )

        TextButton(
            onClick = {
                onEvent.invoke(AulaAndroidState.Navigate(Route.NewAccountScreen))
            }
        ) {
            Text(
                color = Color.Black,
                textDecoration = TextDecoration.Underline,
                text = stringResource(R.string.dont_have_account_text_button)
            )
        }
    }
}