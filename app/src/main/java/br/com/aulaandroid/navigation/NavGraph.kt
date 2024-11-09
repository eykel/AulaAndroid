package br.com.aulaandroid.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.aulaandroid.ui.login.LoginScreen
import br.com.aulaandroid.ui.newAccount.NewAccountScreen
import br.com.aulaandroid.ui.theme.AulaAndroidTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(){
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    var bottomSheetIsOpen = rememberSaveable { mutableStateOf(false) }
    val errorMessage = remember { mutableStateOf("") }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)




    AulaAndroidTheme {
        Scaffold { innerPadding ->
            Column(Modifier.padding(innerPadding)) {
                NavHost(navController, startDestination = Route.LoginScreen) {
                    composable<Route.LoginScreen> {
                        LoginScreen(koinViewModel()){ state->
                            when(state){
                                is AulaAndroidState.Error -> {
                                    errorMessage.value = state.message
                                    bottomSheetIsOpen.value = true
                                }
                                is AulaAndroidState.Navigate -> navController.navigate(state.route)
                            }
                        }
                    }

                    composable<Route.NewAccountScreen> {
                        NewAccountScreen(koinViewModel()) { state ->
                            when(state){
                                is AulaAndroidState.Error -> {
                                    errorMessage.value = state.message
                                    scope.launch { sheetState.show() }
                                    bottomSheetIsOpen.value = true
                                }
                                is AulaAndroidState.Navigate -> navController.navigate(state.route)
                            }
                        }
                    }
                }
            }

            if (bottomSheetIsOpen.value) {
                ModalBottomSheet(
                    onDismissRequest = {
                        bottomSheetIsOpen.value = false
                    },
                    sheetState = sheetState
                ) {
                    // Sheet content
                    Text(errorMessage.value)
                    Button(onClick = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            bottomSheetIsOpen.value = false
                        }
                    }) {
                        Text("Hide bottom sheet")
                    }
                }
            }
        }
    }
}
