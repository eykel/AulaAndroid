package br.com.aulaandroid.ui.newAccount

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.aulaandroid.R
import br.com.aulaandroid.data.model.UserModel
import br.com.aulaandroid.navigation.AulaAndroidState
import br.com.aulaandroid.navigation.Route
import br.com.aulaandroid.ui.components.ButtonCustom
import br.com.aulaandroid.ui.components.TextFieldCustom
import br.com.aulaandroid.ui.components.util.TextFieldType
import br.com.aulaandroid.ui.theme.MyBlue

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

    val validName by viewModel.validName.collectAsState()
    val validEmail by viewModel.validEmail.collectAsState()
    val validPassword by viewModel.validPassword.collectAsState()
    val validBirthDay by viewModel.validBirthDay.collectAsState()
    val newAccountState by viewModel.newAccountState.collectAsState()
    val loading by viewModel.loadingButton.collectAsStateWithLifecycle()

    LaunchedEffect(newAccountState) {
        when(val state = newAccountState){
            is NewAccountState.Failure -> {
                onEvent.invoke(AulaAndroidState.Error(state.ex.message.orEmpty()))
            }
            NewAccountState.Success -> {
                onEvent.invoke(AulaAndroidState.Navigate(Route.LoginScreen))
            }
            NewAccountState.Default -> {}
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
            text = stringResource(R.string.register),
            Modifier.padding(20.dp),
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight(600),
                color = MyBlue
            )
        )

        TextFieldCustom(
            textValue = name,
            onValueChange = { newValue ->
                name = newValue
                viewModel.validName(name)
            },
            labelText = R.string.name_text,
            isError = !validName,
            enabled = !loading
        )

        TextFieldCustom(
            textValue = email,
            onValueChange = { newValue ->
                email = newValue
                viewModel.validEmail(email)
            },
            labelText = R.string.email_text,
            isError = !validEmail,
            enabled = !loading
        )

        TextFieldCustom(
            textValue = birthDay,
            onValueChange = { newValue ->
                birthDay = newValue
                viewModel.validBirthDay(birthDay)
            },
            labelText = R.string.birthday_text,
            isError = !validBirthDay,
            enabled = !loading
        )

        TextFieldCustom(
            textValue = password,
            onValueChange = { newValue ->
                password = newValue
                viewModel.validPassword(password)
            },
            labelText = R.string.password_text,
            isError = !validPassword,
            enabled = !loading,
            type = TextFieldType.Password
        )

        ButtonCustom(
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
            text = R.string.register_button,
            loading = loading
        )
    }
}