// Updated WorkoutItem.kt
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.practice_iv.models.Workout

@Composable
fun WorkoutItem(
    workout: Workout,
    onDelete: () -> Unit,
    onFavorite: () -> Unit,
    favoriteButtonLabel: String
) {

    // Use Icons.Filled.Favorite for filled heart (favorite)
    // Use Icons.Outlined.FavoriteBorder for outlined heart (not favorite)
    val favoriteIcon = if (workout.favorite) {
        Icons.Filled.Favorite
    } else {
        Icons.Outlined.FavoriteBorder
    }

    var showDeleteDialog by remember { mutableStateOf(false) }
    var showFavoriteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Workout") },
            text = { Text("Are you sure you want to delete this workout?") },
            confirmButton = {
                TextButton(onClick = {
                    onDelete()
                    showDeleteDialog = false
                }) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    if (showFavoriteDialog) {
        AlertDialog(
            onDismissRequest = { showFavoriteDialog = false },
            title = { Text("Edit Favorites") },
            text = { Text("Do you want to add/remove this workout to favorites?") },
            confirmButton = {
                TextButton(onClick = {
                    onFavorite()
                    showFavoriteDialog = false
                }) {
                    Text(favoriteButtonLabel)
                }
            },
            dismissButton = {
                TextButton(onClick = { showFavoriteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = workout.name, style = MaterialTheme.typography.titleMedium)
            Text(text = workout.description, style = MaterialTheme.typography.bodyMedium)
        }

        IconButton(onClick = { showFavoriteDialog = true }) {
            Icon(Icons.Filled.Star, contentDescription = "Add to Favorites")
        }
        IconButton(onClick = { showDeleteDialog = true }) {
            Icon(Icons.Filled.Delete, contentDescription = "Delete Workout")
        }
    }
}

