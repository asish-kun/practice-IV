// HomeScreen.kt
package com.example.practice_iv.screens

import WorkoutItem
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.practice_iv.components.AddWorkoutDialog
import com.example.practice_iv.models.Workout
import com.google.firebase.database.*
import kotlinx.coroutines.launch
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomeScreen() {
    val auth = FirebaseAuth.getInstance()
    var userId by remember { mutableStateOf<String?>(auth.currentUser?.uid) }

    val database = FirebaseDatabase.getInstance().getReference("workouts/$userId")

    var workoutList by remember { mutableStateOf<List<Workout>>(emptyList()) }
    var showDialog by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    // Fetch workouts from Firebase
    LaunchedEffect(Unit) {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val workouts = mutableListOf<Workout>()
                for (workoutSnapshot in snapshot.children) {
                    val workout = workoutSnapshot.getValue(Workout::class.java)
                    workout?.let {
                        it.id = workoutSnapshot.key ?: ""
                        workouts.add(it)
                    }
                }
                workoutList = workouts
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }

    if (showDialog) {
        AddWorkoutDialog(
            onDismiss = { showDialog = false },
            onAdd = { name, description ->
                val newWorkoutRef = database.push()
                val workout = Workout(
                    id = newWorkoutRef.key ?: "",
                    name = name,
                    description = description
                )
                newWorkoutRef.setValue(workout).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        showDialog = false
                    } else {
                        // Handle the error
                        Log.e("FirebaseError", "Failed to add workout", task.exception)
                    }
                }
            }
        )
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                showDialog = true
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add Workout")
            }
        }
    ){ padding ->
        Box(modifier = Modifier.padding(padding)) {
            if (workoutList.isEmpty()) {
                // Display a message when the list is empty
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No workouts available. Tap '+' to add one.")
                }
            } else {
                LazyColumn {
                    items(workoutList.size) { index ->
                        val workout = workoutList[index]
                        WorkoutItem(
                            workout = workout,
                            onDelete = {
                                database.child(workout.id).removeValue()
                            },
                            onFavorite = {
                                val newFavoriteStatus = !workout.favorite
                                database.child(workout.id)
                                    .child("favorite")
                                    .setValue(newFavoriteStatus)
                                    .addOnCompleteListener { task ->
                                        if (!task.isSuccessful) {
                                            // Handle the error
                                            Log.e(
                                                "FirebaseError",
                                                "Failed to update favorite status",
                                                task.exception
                                            )
                                        }
                                    }
                            },
                            favoriteButtonLabel = "Add to Favorites"
                        )
                    }
                }
            }
        }
    }
}
