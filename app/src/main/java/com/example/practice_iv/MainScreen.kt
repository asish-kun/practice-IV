// MainScreen.kt
package com.example.practice_iv

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.practice_iv.screens.FavoritesScreen
import com.example.practice_iv.screens.HomeScreen
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    isDarkTheme: Boolean,
    toggleTheme: () -> Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItem by remember { mutableStateOf("Home") }

    val navItems = listOf("Home", "Favorites", "Account Information", "Log Out")

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(12.dp))
                navItems.forEach { item ->
                    NavigationDrawerItem(
                        label = { Text(item) },
                        selected = selectedItem == item,
                        onClick = {
                            selectedItem = item
                            scope.launch { drawerState.close() }
                        },
                        icon = {
                            when (item) {
                                "Home" -> Icon(Icons.Default.Home, contentDescription = null)
                                "Favorites" -> Icon(Icons.Default.Favorite, contentDescription = null)
                                "Account Information" -> Icon(Icons.Default.AccountCircle, contentDescription = null)
                                "Log Out" -> Icon(Icons.Default.ExitToApp, contentDescription = null)
                            }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                SmallTopAppBar(
                    title = { Text(selectedItem) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                when (selectedItem) {
                    "Home" -> HomeScreen()
                    "Favorites" -> FavoritesScreen()
                    "Account Information" -> AccountDetailsPage(navController, toggleTheme)
                    "Log Out" -> {
                        FirebaseAuth.getInstance().signOut()
                        navController.navigate("landing") {
                            popUpTo("account") { inclusive = true }
                        }
                    }
                }
            }
        }
    }
}
