package com.kreedaprerana.scout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kreedaprerana.scout.ui.screens.AddAthleteScreen
import com.kreedaprerana.scout.ui.screens.HomeScreen
import com.kreedaprerana.scout.ui.screens.SplashScreen
import com.kreedaprerana.scout.ui.screens.TrialLoggerScreen
import com.kreedaprerana.scout.ui.theme.KreedaPreranaScoutTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KreedaPreranaScoutTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ScoutAppNavigation()
                }
            }
        }
    }
}

@Composable
fun ScoutAppNavigation() {
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen(onTimeout = {
                navController.navigate("home") {
                    popUpTo("splash") { inclusive = true }
                }
            })
        }
        composable("home") {
            HomeScreen(
                onAddAthleteClick = {
                    navController.navigate("add_athlete")
                },
                onStartTrialClick = {
                    navController.navigate("trial_logger")
                }
            )
        }
        composable("add_athlete") {
            AddAthleteScreen(onBack = {
                navController.popBackStack()
            })
        }
        composable("trial_logger") {
            TrialLoggerScreen(onBack = {
                navController.popBackStack()
            })
        }
        composable("login") {
            LoginScreenPlaceholder()
        }
    }
}

@Composable
fun LoginScreenPlaceholder() {
    // Basic placeholder for the next screen
    androidx.compose.foundation.layout.Column(
        modifier = androidx.compose.foundation.layout.Modifier.fillMaxSize(),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        androidx.compose.material3.Text("Login Screen Placeholder")
    }
}
