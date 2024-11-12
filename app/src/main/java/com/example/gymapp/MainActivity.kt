package com.example.gymapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gymapp.ui.screens.login.LoginScreen
import com.example.gymapp.ui.screens.login.LoginViewModel
import com.example.gymapp.ui.screens.admin.AdminScreen
import com.example.gymapp.ui.screens.admin.AdminViewModel
import com.example.gymapp.ui.screens.register.RegisterScreen
import com.example.gymapp.ui.screens.register.RegisterViewModel
import com.example.gymapp.ui.screens.user.UserScreen
import com.example.gymapp.ui.screens.user.UserViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.White
            ) {
                NavHost(navController = navController, startDestination = "login") {
                    composable("login") {
                        LoginScreen(LoginViewModel(), navController)
                    }
                    composable("register") {
                        RegisterScreen(RegisterViewModel(), navController)
                    }
                    composable("admin") {
                        AdminScreen(AdminViewModel(), navController)
                    }
                    composable("user") {
                        UserScreen(UserViewModel(), navController)
                    }
                }
            }
        }
    }
}
