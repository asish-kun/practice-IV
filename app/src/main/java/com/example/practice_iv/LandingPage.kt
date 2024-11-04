// LandingPage.kt
package com.example.practice_iv

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun LandingPage(navController: NavController) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Welcome to Your App", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = { navController.navigate("signin") }) {
                Text(text = "Sign In")
            }
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = { navController.navigate("signup") }) {
                Text(text = "Sign Up")
            }
        }
    }
}
