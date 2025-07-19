package br.com.aulaandroid.ui.newAccount

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
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
import br.com.aulaandroid.util.formatDate

@Composable
fun NewAccountScreen(
    viewModel: NewAccountViewModel,
    onEvent: (AulaAndroidState) -> Unit,
){
    Content(viewModel, onEvent)
}


@Composable
private fun Content(viewModel: NewAccountViewModel, onEvent: (AulaAndroidState) -> Unit){
    var name by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue()) }
    var email by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue()) }
    var password by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue()) }
    var birthDay by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }

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
                viewModel.validName(name.text)
            },
            labelText = R.string.name_text,
            isError = !validName,
            enabled = !loading
        )

        TextFieldCustom(
            textValue = email,
            onValueChange = { newValue ->
                email = newValue
                viewModel.validEmail(email.text)
            },
            labelText = R.string.email_text,
            isError = !validEmail,
            enabled = !loading
        )

        TextFieldCustom(
            textValue = birthDay,
            onValueChange = { newValue ->
                val masked = newValue.text.formatDate()
                birthDay = TextFieldValue(text = masked, selection = TextRange(masked.length))
                viewModel.validBirthDay(masked)
            },
            labelText = R.string.birthday_text,
            isError = !validBirthDay,
            enabled = !loading,
        )

        TextFieldCustom(
            textValue = password,
            onValueChange = { newValue ->
                password = newValue
                viewModel.validPassword(password.text)
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
                        name = name.text,
                        birthDay = birthDay.text,
                        password = password.text,
                        email = email.text
                    )
                )
            },
            text = R.string.register_button,
            loading = loading
        )
    }
}

