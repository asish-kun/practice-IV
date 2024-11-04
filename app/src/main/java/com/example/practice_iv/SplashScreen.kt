// SplashScreen.kt
package com.example.practice_iv

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import androidx.navigation.NavController

@Composable
fun SplashScreen(navController: NavController) {
    // Navigate to Landing Page after a delay
    LaunchedEffect(key1 = true) {
        delay(4000) // 4 seconds
        navController.navigate("landing") {
            popUpTo("splash") { inclusive = true }
        }
    }

    // Display the GIF
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        AsyncImage(
            model = "file:///android_asset/logo.jpg",
            contentDescription = "Animated Logo",
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(300.dp)
        )
    }
}
