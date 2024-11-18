package com.example.gymapp.ui.screens.register
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.gymapp.ui.screens.login.LoginStatus
import kotlinx.coroutines.launch
import android.util.Log

@Composable
fun RegisterScreen(viewModel: RegisterViewModel, navController: NavHostController) {

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val status by viewModel.status.observeAsState(LoginStatus.Idle)

    LaunchedEffect(status) {
        if (status == RegisterStatus.Success) {
            navController.navigate("user")
        } else if (status == LoginStatus.Failure) {
            viewModel.loginErrorMessage.value?.let {
                scope.launch {
                    snackbarHostState.showSnackbar("$it")
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { Register(Modifier.padding(it), viewModel, navController) }
    )

}
@Composable
fun Register(modifier: Modifier, viewModel: RegisterViewModel, navController: NavHostController) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { UserForm(Modifier.padding(it), viewModel, navController) }
    )
}

@Composable
fun UserForm(modifier: Modifier, viewModel: RegisterViewModel, navController: NavHostController) {
    val name by viewModel.name.observeAsState("Santiago")
    val lastName by viewModel.lastName.observeAsState("Moreno")
    val email by viewModel.email.observeAsState("jampiersantiago@gmail.com")
    val password by viewModel.password.observeAsState("Github07")
    val weight by viewModel.weight.observeAsState("100")
    val height by viewModel.height.observeAsState("1.80")
    val rh by viewModel.rh.observeAsState("A+")
    val gender by viewModel.gender.observeAsState("M")
    val dateOfBirthDay by viewModel.dateOfBirthDay.observeAsState("2023-10-05")
    val occupation by viewModel.occupation.observeAsState("Desarrollador de software")
    val coroutineScope = rememberCoroutineScope()

    var colors = TextFieldDefaults.colors(
        focusedTextColor = Color(0xFF636262),
        unfocusedTextColor = Color(0xFF636262),
        focusedContainerColor = Color(0xFFFAFAFA),
        unfocusedContainerColor = Color(0xFFFAFAFA),
    )

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        TextField(
            value = name,
            onValueChange = { viewModel.onNameChanged(it) },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = colors
        )
        TextField(
            value = lastName,
            onValueChange = { viewModel.onLastNameChanged(it) },
            label = { Text("Apellido") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = colors
        )
        TextField(
            value = email,
            onValueChange = { viewModel.onEmailChanged(it) },
            label = { Text("Correo Electrónico") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            colors = colors
        )
        TextField(
            value = password,
            onValueChange = { viewModel.onPasswordChanged(it) },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            colors = colors
        )
        TextField(
            value = weight,
            onValueChange = { viewModel.onWeightChanged(it) },
            label = { Text("Peso (kg)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            colors = colors
        )
        TextField(
            value = height,
            onValueChange = { viewModel.onHeightChanged(it) },
            label = { Text("Altura (m)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            colors = colors
        )
        TextField(
            value = rh,
            onValueChange = { viewModel.onRhChanged(it) },
            label = { Text("Tipo de Sangre (RH)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = colors

        )
        TextField(
            value = gender,
            onValueChange = { viewModel.onGenderChanged(it) },
            label = { Text("Género") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = colors
        )
        TextField(
            value = dateOfBirthDay,
            onValueChange = { viewModel.onDateOfBirthChanged(it) },
            label = { Text("Fecha de Nacimiento") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            colors = colors
        )
        TextField(
            value = occupation,
            onValueChange = { viewModel.onOccupationChanged(it) },
            label = { Text("Ocupación") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = colors
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                coroutineScope.launch {
                    viewModel.saveUserData()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("Guardar")
        }
        Button(
            onClick = {
                navController.navigate("login")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp)
                .padding(top = 16.dp)

        ) {
            Text("Ir al login")
        }
    }
}
