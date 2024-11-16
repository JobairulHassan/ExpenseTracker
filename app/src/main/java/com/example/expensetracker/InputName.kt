package com.example.expensetracker

import android.app.Activity
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun InputName(navController: NavController) {
    var username = remember { mutableStateOf("") }
    val context = LocalContext.current
    val sharedPref = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

    BackHandler {
        // Prevent the back press action
        (navController.context as? ComponentActivity)?.finish()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.teal_700)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo image
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Splash Screen Logo",
                modifier = Modifier
                    .size(200.dp)
            )

            // Welcome text
            Text(
                text = "Welcome to Expense Tracker",
                color = Color.White,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.size(16.dp))

            // Username input field
            OutlinedTextField(
                value = username.value,
                onValueChange = { username.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White),
                placeholder = { Text("Enter Your Name") },
                singleLine = true,

            )

            Spacer(modifier = Modifier.size(16.dp))


        }
        // Submit button (enabled when username is not empty)
        IconButton(
            onClick = {
                with(sharedPref.edit()) {
                    putString("userName", username.value)
                    apply()
                }

                navController.navigate("/home"){
                    popUpTo("/name_input") { inclusive = true }
                    launchSingleTop = true
                }
            },
            enabled = username.value.isNotBlank(),
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd)
                .size(64.dp),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.arrow_circle_right),
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}

@Composable
@Preview(
    showBackground = true,
    showSystemUi = true
)
fun PreviewInputName() {
    InputName(rememberNavController())
}