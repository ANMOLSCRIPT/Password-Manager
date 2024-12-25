package com.example.passwordmanager

import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.passwordmanager.ui.theme.PinkTheme
import com.example.passwordmanager.ui.theme.Red


@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current


    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Password",
            fontSize = 50.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )
        Text(
            text = "Manager",
            fontSize = 50.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(10.dp))


        // Username TextField
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Enter your Email") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            textStyle = TextStyle(color = Color.Black)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password TextField
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Enter your Password") },
            textStyle = TextStyle(color = Color.Black),
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                if (passwordVisibility) {
                    IconButton(onClick = { passwordVisibility = false }) {
                        Icon(
                            imageVector = Icons.Filled.Visibility,
                            contentDescription = "show"
                        )
                    }
                } else {
                    IconButton(
                        onClick = { passwordVisibility = true }) {
                        Icon(
                            imageVector = Icons.Filled.VisibilityOff,
                            contentDescription = "hide"
                        )
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))


        Button(
            onClick = {
                if (validate(username, password)) {

                    Toast.makeText(context, "Login successful!", Toast.LENGTH_SHORT).show()
                    navController.navigate(Screen.HomeScreen.route) {


                        popUpTo(Screen.LoginScreen.route) {
                            inclusive = true
                        }
                    }
                } else {

                    Toast.makeText(context, "Invalid username or password", Toast.LENGTH_SHORT)
                        .show()
                }
            },
            modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(PinkTheme)
        ) {
            Text("Login", fontWeight = FontWeight.SemiBold)
        }
    }
}


// Validation function
fun validate(username: String, password: String): Boolean {
    if (username.isEmpty() || password.isEmpty()) {
        return false
    }

    if (password.length < 6) {
        return false
    }

    if(username== "test@admin.com" && password=="12345678") {
        return true
    }

    return false
}