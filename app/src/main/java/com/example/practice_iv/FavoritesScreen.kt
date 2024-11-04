// FavoritesScreen.kt
package com.example.practice_iv.screens

import WorkoutItem
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.practice_iv.models.Workout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch

@Composable
fun FavoritesScreen() {
    val auth = FirebaseAuth.getInstance()
    val userId = auth.currentUser?.uid ?: return

    val database = FirebaseDatabase.getInstance().getReference("workouts/$userId")

    var favoritesList by remember { mutableStateOf<List<Workout>>(emptyList()) }

    val scope = rememberCoroutineScope()

    // Fetch favorites from Firebase
    LaunchedEffect(Unit) {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val workouts = mutableListOf<Workout>()
                for (workoutSnapshot in snapshot.children) {
                    val workout = workoutSnapshot.getValue(Workout::class.java)
                    workout?.let {
                        it.id = workoutSnapshot.key ?: ""
                        if (it.favorite) { // Filter workouts where favorite == true
                            workouts.add(it)
                        }
                    }
                }
                favoritesList = workouts
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FirebaseError", "Error fetching data", error.toException())
            }
        })
    }

    Scaffold { padding ->
        Box(modifier = Modifier.padding(padding)) {
            if (favoritesList.isEmpty()) {
                // Display a message when the list is empty
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No favorite workouts available.")
                }
            } else{
                LazyColumn {
                    items(favoritesList.size) { index ->
                        val workout = favoritesList[index]
                        WorkoutItem(
                            workout = workout,
                            onDelete = {
                                database.child(workout.id).removeValue()
                            },
                            onFavorite = {
                                // Toggle favorite status
                                val newFavoriteStatus = !workout.favorite
                                database.child(workout.id)
                                    .child("favorite")
                                    .setValue(newFavoriteStatus)
                                    .addOnCompleteListener { task ->
                                        if (!task.isSuccessful) {
                                            Log.e(
                                                "FirebaseError",
                                                "Failed to update favorite status",
                                                task.exception
                                            )
                                        }
                                    }
                            },
                            favoriteButtonLabel = "Remove from Favorites"
                        )
                    }
                }
            }
        }
    }
}
