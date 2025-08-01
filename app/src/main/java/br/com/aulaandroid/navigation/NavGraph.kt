package br.com.aulaandroid.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import br.com.aulaandroid.data.local.utils.SessionManager
import br.com.aulaandroid.ui.components.BottomSheetV1
import br.com.aulaandroid.ui.detail.DetailScreen
import br.com.aulaandroid.ui.favorite.FavoriteScreen
import br.com.aulaandroid.ui.home.HomeScreen
import br.com.aulaandroid.ui.login.LoginScreen
import br.com.aulaandroid.ui.newAccount.NewAccountScreen
import br.com.aulaandroid.ui.settings.SettingsScreen
import br.com.aulaandroid.ui.theme.AulaAndroidTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.inject
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph() {
    val navController = rememberNavController()
    var bottomSheetIsOpen by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()
    var selectedItem by remember { mutableIntStateOf(1) }
    val sessionManager: SessionManager by inject()
    val sessionState by sessionManager.session.collectAsState()


    AulaAndroidTheme {
        Scaffold (
            bottomBar = {
                if(sessionState?.logged == true){
                    NavigationBar {
                        topLevelRoute.forEachIndexed { index, item ->
                            NavigationBarItem(
                                icon = { Icon(
                                    if (selectedItem == index) item.selectedIcon else item.unselectedIcon,
                                    contentDescription = ""
                                ) },
                                label = { Text(item.name) },
                                selected = selectedItem == index,
                                onClick = {
                                    navController.navigate(route = item.route)
                                    selectedItem = index
                                }
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            Column(Modifier.padding(innerPadding).fillMaxSize()) {
                NavHost(navController, startDestination = if(sessionState?.logged == true) Route.HomeScreen else Route.LoginScreen) {
                    composable<Route.LoginScreen> {
                        LoginScreen(koinViewModel()) { state ->
                            handleScreenState(
                                state = state,
                                navController = navController,
                                updateErrorMessage = { message -> errorMessage = message },
                                updateBottomSheetState = { isOpen ->
                                    if (isOpen) {
                                        coroutineScope.launch {
                                            sheetState.show()
                                        }
                                    }
                                    bottomSheetIsOpen = isOpen
                                },
                                sheetState = sheetState,
                            )
                        }
                    }

                    composable<Route.NewAccountScreen> {
                        NewAccountScreen(koinViewModel()) { state ->
                            handleScreenState(
                                state = state,
                                navController = navController,
                                updateErrorMessage = { message -> errorMessage = message },
                                updateBottomSheetState = { isOpen ->
                                    if (isOpen) {
                                        coroutineScope.launch {
                                            sheetState.show()
                                        }
                                    }
                                    bottomSheetIsOpen = isOpen
                                },
                                sheetState = sheetState,
                            )
                        }
                    }

                    composable<Route.HomeScreen> {
                        HomeScreen(koinViewModel()) { state ->
                            handleScreenState(
                                state = state,
                                navController = navController,
                                updateErrorMessage = { message -> errorMessage = message },
                                updateBottomSheetState = { isOpen ->
                                    if (isOpen) {
                                        coroutineScope.launch {
                                            sheetState.show()
                                        }
                                    }
                                    bottomSheetIsOpen = isOpen
                                },
                                sheetState = sheetState,
                            )
                        }
                    }

                    composable<Route.DetailScreen> { backStackEntry ->
                        val param: Route.DetailScreen = backStackEntry.toRoute()
                        DetailScreen(koinViewModel(), param.nickName)
                    }

                    composable<Route.FavoriteScreen> { backStackEntry ->
                        FavoriteScreen(koinViewModel()) { state ->
                            handleScreenState(
                                state = state,
                                navController = navController,
                                updateErrorMessage = { message -> errorMessage = message },
                                updateBottomSheetState = { isOpen ->
                                    if (isOpen) {
                                        coroutineScope.launch {
                                            sheetState.show()
                                        }
                                    }
                                    bottomSheetIsOpen = isOpen
                                },
                                sheetState = sheetState,
                            )
                        }
                    }

                    composable<Route.SettingScreen> { backStackEntry ->
                        SettingsScreen(koinViewModel()) { state ->
                            handleScreenState(
                                state = state,
                                navController = navController,
                                updateErrorMessage = { message -> errorMessage = message },
                                updateBottomSheetState = { isOpen ->
                                    if (isOpen) {
                                        coroutineScope.launch {
                                            sheetState.show()
                                        }
                                    }
                                    bottomSheetIsOpen = isOpen
                                },
                                sheetState = sheetState,
                            )
                        }
                    }
                }
            }

            if (bottomSheetIsOpen) {
                ModalBottomSheet(
                    onDismissRequest = {
                        coroutineScope.launch {
                            sheetState.hide()
                            bottomSheetIsOpen = false
                        }
                    },
                    sheetState = sheetState,
                    containerColor = Color.Transparent
                ) {
                    // Sheet content
                    BottomSheetV1(errorMessage)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
fun handleScreenState(
    state: AulaAndroidState,
    navController: NavHostController,
    updateErrorMessage: (String) -> Unit,
    updateBottomSheetState: (Boolean) -> Unit,
    sheetState: SheetState,
) {
    when (state) {
        is AulaAndroidState.Error -> {
            updateErrorMessage(state.message)
            if (!sheetState.isVisible) {
                updateBottomSheetState(true)
            }
        }

        is AulaAndroidState.Navigate -> {
            navController.navigate(state.route)
        }
    }
}