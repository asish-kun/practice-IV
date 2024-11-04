// AccountDetailsPage.kt
package com.example.practice_iv

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AccountDetailsPage(
    navController: NavController,
    toggleTheme: () -> Unit
) {
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Account Information", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(20.dp))

        user?.let {
            Text(text = "Email: ${it.email}")
            // Add more user info if needed
        } ?: run {
            Text(text = "No user information available.")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                toggleTheme()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Toggle Theme")
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}
