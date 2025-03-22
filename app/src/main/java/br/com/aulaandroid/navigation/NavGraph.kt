package br.com.aulaandroid.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import br.com.aulaandroid.ui.components.BottomSheetV1
import br.com.aulaandroid.ui.detail.DetailScreen
import br.com.aulaandroid.ui.home.HomeScreen
import br.com.aulaandroid.ui.login.LoginScreen
import br.com.aulaandroid.ui.newAccount.NewAccountScreen
import br.com.aulaandroid.ui.theme.AulaAndroidTheme
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(){
    val navController = rememberNavController()
    //Bottomsheet problemática, se eu usar remember, ele trava a tela, se eu utiliza
    //rememberSaveable, ele nunca mais volta.
    val bottomSheetIsOpen = rememberSaveable { mutableStateOf(false) }
    val errorMessage = remember { mutableStateOf("") }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)


    AulaAndroidTheme {
        Scaffold { innerPadding ->
            Column(Modifier.padding(innerPadding)) {
                NavHost(navController, startDestination = Route.LoginScreen) {
                    composable<Route.LoginScreen> {
                        LoginScreen(koinViewModel()){ state ->
                            when(state){
                                is AulaAndroidState.Error -> {
                                    errorMessage.value = state.message
                                    bottomSheetIsOpen.value = true
                                }
                                is AulaAndroidState.Navigate -> {
                                    navController.navigate(state.route)
                                }
                            }
                        }
                    }

                    composable<Route.NewAccountScreen> {
                        NewAccountScreen(koinViewModel()) { state ->
                            when(state){
                                is AulaAndroidState.Error -> {
                                    errorMessage.value = state.message
                                    bottomSheetIsOpen.value = true
                                }
                                is AulaAndroidState.Navigate -> navController.navigate(state.route)
                            }
                        }
                    }

                    composable<Route.HomeScreen> {
                        HomeScreen(koinViewModel()) { state ->
                            when(state){
                                is AulaAndroidState.Error -> {
                                    errorMessage.value = state.message
                                    bottomSheetIsOpen.value = true
                                }
                                is AulaAndroidState.Navigate -> navController.navigate(state.route)
                            }
                        }
                    }

                    composable<Route.DetailScreen> { backStackEntry ->
                        val param: Route.DetailScreen = backStackEntry.toRoute()
                        DetailScreen(koinViewModel(), param.nickName){ state ->
                            when(state){
                                is AulaAndroidState.Error -> {
                                    errorMessage.value = state.message
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
                    sheetState = sheetState,
                    containerColor = Color.Transparent
                ) {
                    // Sheet content
                    BottomSheetV1(errorMessage.value)
                }
            }
        }
    }
}
