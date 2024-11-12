package com.example.gymapp.ui.screens.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.gymapp.ui.screens.admin.Admin
import com.example.gymapp.ui.screens.admin.UserCard
import com.example.gymapp.ui.screens.login.Login
import com.example.gymapp.ui.screens.login.LoginStatus
import com.example.gymapp.ui.screens.login.LoginViewModel
import kotlinx.coroutines.launch

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun UserScreen(viewModel: UserViewModel, navController: NavHostController) {
    LaunchedEffect(Unit) {
        viewModel.onLoadCategories()
    }

    Scaffold(
        content = { UserView(Modifier.padding(it), viewModel, navController) }
    )
}

@Composable
fun UserView(modifier: Modifier, viewModel: UserViewModel, navController: NavHostController) {

    val isLoading by viewModel.isLoading.observeAsState(initial = false)
    val categories by viewModel.category.observeAsState(initial = emptyList())
    val exercises by viewModel.excercises.observeAsState(initial = emptyList())
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
            Column(modifier = Modifier.fillMaxSize()) {
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

                // Row for Category Tabs
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    categories.forEach { category ->
                        Tab(
                            text = category.name ?: "N/A",
                            selected = false,
                            onClick = {
                                coroutineScope.launch {
                                    viewModel.onLoadExcercises(category.id ?: 0)
                                }
                            }
                        )
                    }
                }

                // List of Exercises
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(exercises.size) { index ->
                        val ex = exercises[index]

                        Card(
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(4.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.LightGray)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = ex.id.toString(),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Black
                                )
                                Text(
                                    text = ex.name.toString(),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Black
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = ex.description.toString(),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Black
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun Tab(text: String, selected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(8.dp, top = 48.dp)
            .clip(RoundedCornerShape(50))
            .background(Color.Black)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            color = Color.White
        )
    }
}

