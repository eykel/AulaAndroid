package br.com.aulaandroid.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import br.com.aulaandroid.navigation.AulaAndroidState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.aulaandroid.navigation.Route


@Composable
fun SettingsScreen(viewModel: SettingsViewModel, onEvent: (AulaAndroidState) -> Unit) {

    Content(viewModel = viewModel, onEvent = onEvent)
}

@Composable
private fun Content(viewModel: SettingsViewModel, onEvent: (AulaAndroidState) -> Unit) {

    val settingsState by viewModel.settingsState.collectAsState()

    LaunchedEffect(settingsState) {
        when(val state = settingsState){
            is SettingsState.Failure -> {
                onEvent.invoke(AulaAndroidState.Error(state.ex.message.orEmpty()))
            }
            SettingsState.Success -> {
                onEvent.invoke(AulaAndroidState.Navigate(Route.LoginScreen))
            }
            SettingsState.Default -> {}
        }
    }

    Column(Modifier.fillMaxSize()) {
        Text(
            text = "Configuração",
            fontSize = 24.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.weight(1f))
        Row(
            Modifier
                .fillMaxWidth()
                .clickable{ viewModel.logout() }
                .padding(vertical = 16.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.ExitToApp,
                contentDescription = "",
                tint = Color.Red,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Text(
                text = "Sair",
                color = Color.Red,
            )
        }
    }
}