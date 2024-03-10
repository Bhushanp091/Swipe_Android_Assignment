package com.example.swipe_android_assignment.util.shared_components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.swipe_android_assignment.ui.theme.BlueTheme

@Composable
fun CommonProgressBar() {
    CircularProgressIndicator(
        color = BlueTheme,
        modifier = Modifier.size(40.dp),
        strokeWidth = 3.dp // You can adjust the thickness of the progress indicator
    )
}