package com.example.gymapp.ui.screens.login

import android.util.Log
import android.util.Patterns

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gymapp.data.auth.DataRepository
import com.example.gymapp.domain.user.LoginCredentials
import com.example.gymapp.domain.user.Session

sealed class LoginStatus {
    object Idle : LoginStatus()
    object SuccessAdmin : LoginStatus()
    object SuccessUser : LoginStatus()
    object Failure : LoginStatus()
}

class LoginViewModel: ViewModel() {

    private val _status = MutableLiveData<LoginStatus>()
    val status: MutableLiveData<LoginStatus> = _status

    private val _session = MutableLiveData<Session?>()
    val session: MutableLiveData<Session?> = _session

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _loginEnable = MutableLiveData<Boolean>()
    val loginEnable: LiveData<Boolean> = _loginEnable

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _loginErrorMessage = MutableLiveData<String?>()
    val loginErrorMessage: MutableLiveData<String?> = _loginErrorMessage

    fun onLoginChanged(email: String, password: String) {
        _email.value = email
        _password.value = password
        _loginEnable.value = isValidEmail(email) && isValidPassword(password)
    }

    private fun isValidPassword(password: String): Boolean = password.length > 1

    private fun isValidEmail(email: String): Boolean  = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    suspend fun onLoginSelected() {
        _isLoading.value = true

        var response: Result<Session> = DataRepository().loginUser(
            LoginCredentials(
                email = _email.value ?: "",
                password = _password.value ?: "",
            )
        )

        if (response.isSuccess) {
            var c  = response.getOrNull()
            Log.d("LoginViewModel", "Login successful: $c")


            if(c == null ){

                _session.value = c
                _status.value = LoginStatus.Failure
                _loginErrorMessage.value = "null"

            }else{

                if(c.user?.rol?.id == 1){
                    _status.value = LoginStatus.SuccessAdmin;
                }else{
                    _status.value = LoginStatus.SuccessUser;
                }

                _session.value = c
                _loginErrorMessage.value = null
            }

        }else{
            var l = response.exceptionOrNull()?.message ?: "Unknown error"
            Log.d("LoginViewModel", "Error during login: $l")
            _loginErrorMessage.value = "Credenciales incorrectas"
            _status.value = LoginStatus.Failure
        }




//        var response: Result<List<Rol>> = RoleRepository().fetchRoles()
//
//        if (response.isSuccess) {
//            var c  = response.getOrNull()
//            Log.d("LoginViewModel", "Roles fetched successfully: $c")
//        } else {
//            var l = response.exceptionOrNull()?.message ?: "Unknown error"
//            Log.d("LoginViewModel", "Error fetching roles: $l")
//        }

        _isLoading.value = false
    }



    private fun mockLogin(credentials: LoginCredentials): Session? {
        // Simulación: reemplazar con la llamada real a la API
        return if (credentials.email == "test@example.com" && credentials.password == "password") {
            Session(user = null, token = "mockToken123")
        } else {
            null
        }

    }
}