package com.example.practice_iv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.practice_iv.ui.theme.PracticeIVTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isDarkTheme by remember { mutableStateOf(false) }
            PracticeIVTheme (darkTheme = isDarkTheme) {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "landing") {
                    composable("landing") { LandingPage(navController) }
                    composable("signin") { SignInPage(navController) }
                    composable("signup") { SignUpPage(navController) }
                    composable("account") {
                        MainScreen(navController, isDarkTheme) { isDarkTheme = !isDarkTheme }
                    }
                }
            }
        }
    }
}
