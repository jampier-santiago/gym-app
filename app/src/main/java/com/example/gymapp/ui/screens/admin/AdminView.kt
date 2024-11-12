package com.example.gymapp.ui.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.gymapp.domain.user.User
import com.example.gymapp.ui.screens.login.Login
import com.example.gymapp.ui.screens.login.LoginViewModel
import kotlinx.coroutines.launch

@Composable
fun AdminScreen(viewModel: AdminViewModel, navController: NavHostController) {
    LaunchedEffect(Unit) {
        viewModel.onLoadUsers()
    }

    Scaffold(
        content = { Admin(Modifier.padding(it), viewModel, navController) }
    )
}

@Composable
fun Admin(modifier: Modifier, viewModel: AdminViewModel, navController: NavHostController) {
    val isLoading by viewModel.isLoading.observeAsState(initial = false)
    val users by viewModel.users.observeAsState(initial = emptyList())
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
            .padding(16.dp)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color.White
            )
        } else {
            Column () {
                Button(
                    onClick = {
                        navController.navigate("login")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp)
                        .padding(top = 16.dp)

                ) {
                    Text("Cerrar Sesion")
                }

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(users.size) { index ->

                        val user = users[index]
                        UserCard(user, onDeleteClick = {
                            coroutineScope.launch {
                                viewModel.onDelete(user.id?:0)
                            }

                        }
                        )
                    }
                }
            }

        }
    }
}
@Composable
fun UserCard(user: User, onDeleteClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(),
        colors = CardDefaults.cardColors(containerColor = Color.Black)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Nombre: ${user.name}",
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Correo: ${user.email}",
                color = Color.White,
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onDeleteClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                shape = RoundedCornerShape(50),
                modifier = Modifier.align(Alignment.End)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Eliminar", color = Color.White)
            }
        }
    }
}

