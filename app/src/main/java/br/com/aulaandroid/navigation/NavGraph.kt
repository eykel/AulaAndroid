package br.com.aulaandroid.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.aulaandroid.ui.login.LoginScreen
import br.com.aulaandroid.ui.newAccount.NewAccountScreen
import br.com.aulaandroid.ui.theme.AulaAndroidTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavGraph(){
    val navController = rememberNavController()

    AulaAndroidTheme {
        Scaffold { innerPadding ->
            Column(Modifier.padding(innerPadding)) {
                NavHost(navController, startDestination = Route.LoginScreen) {
                    composable<Route.LoginScreen> {
                        LoginScreen(
                            koinViewModel()
                        ) { navController.navigate(it)  }
                    }

                    composable<Route.NewAccountScreen> {
                        NewAccountScreen(koinViewModel())
                    }
                }
            }
        }
    }


}