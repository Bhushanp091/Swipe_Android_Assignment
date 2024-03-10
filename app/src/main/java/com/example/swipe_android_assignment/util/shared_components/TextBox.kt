package com.example.social_media_app.util.ui_components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.example.swipe_android_assignment.ui.theme.BlueTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextBox(inputText:String, label:String, onValueChange:(String)->Unit){
    OutlinedTextField(
        value = inputText,
        onValueChange = { onValueChange(it) },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
        placeholder = { Text(text = label) },
        singleLine = true,
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = BlueTheme,
            containerColor = MaterialTheme.colorScheme.surface,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.onBackground,
            focusedIndicatorColor = BlueTheme,

            ),

        shape = RoundedCornerShape(15.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextBoxNumber(inputText:String, label:String, onValueChange:(String)->Unit){
    OutlinedTextField(
        value = inputText,
        onValueChange = { onValueChange(it) },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        placeholder = { Text(text = label) },
        singleLine = true,
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = BlueTheme,
            containerColor = MaterialTheme.colorScheme.surface,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.onBackground,
            focusedIndicatorColor = BlueTheme,

            ),

        shape = RoundedCornerShape(15.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTextBox(inputText:String, label:String, onValueChange:(String)->Unit){
    OutlinedTextField(
        value = inputText,
        onValueChange = { onValueChange(it) },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
        placeholder = { Text(text = label) },
        singleLine = true,
        maxLines = 1,
        leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = BlueTheme) },
        trailingIcon = { IconButton(onClick = {onValueChange("") }) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "Cut")
        }},
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = BlueTheme,
            containerColor = MaterialTheme.colorScheme.surface,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.onBackground,
            focusedIndicatorColor = BlueTheme,

            ),
        shape = RoundedCornerShape(15.dp)
    )
}