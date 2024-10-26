package br.com.aulaandroid.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.aulaandroid.R
import br.com.aulaandroid.navigation.Route
import br.com.aulaandroid.ui.theme.AulaAndroidTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(viewModel: LoginViewModel, maria: (route: Route) -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    val validEmail by viewModel.validEmail.collectAsState()
    val register by viewModel.register.collectAsState()

    if(register){
        Text(text = "O USUÁRIO FOI REGISTRADO")
    }else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .paint(
                    painter = painterResource(id = R.drawable.img),
                    contentScale = ContentScale.Crop
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            Text(text = "Meu APP", Modifier.padding(20.dp))

            TextField(
                modifier = Modifier.padding(20.dp),
                value = email,
                onValueChange = { newValue ->
                    email = newValue
                    viewModel.validEmail(email)
                },
                label = { Text(text = "Email") },
                placeholder = { Text(text = "Email") },
                isError = !validEmail,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Blue,
                )
            )


            TextField(
                value = password,
                onValueChange = { newValuer ->
                    password = newValuer


                },
                singleLine = true,
                label = { Text(text = "Senha") },
                placeholder = { Text(text = "Senha") },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Filled.Lock
                    else Icons.Filled.Lock

                    val description = if (passwordVisible) "Hide password" else "Show password"

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, description)
                    }
                },
                colors = TextFieldDefaults.colors(
                    errorIndicatorColor = Color.Blue,
                    focusedTextColor = Color.Blue
                )
            )

            Button(
                modifier = Modifier.padding(10.dp),
                onClick = {
                    //navigate.invoke()
                }
            ) {
                Text(
                    text = "Login",
                )
            }

            TextButton(
                onClick = { /*TODO*/ }
            ) {
                Text(
                    color = Color.White,
                    textDecoration = TextDecoration.Underline,
                    text = "Se esqueceu sua senha, clique aqui!"
                )
            }

            TextButton(
                onClick = {
                    maria.invoke(Route.NewAccountScreen)
                }
            ) {
                Text(
                    color = Color.White,
                    textDecoration = TextDecoration.Underline,
                    text = "Não possui conta? Cadastrar-se"
                )
            }
        }
    }
}