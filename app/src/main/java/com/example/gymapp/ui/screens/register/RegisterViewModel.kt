package com.example.gymapp.ui.screens.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gymapp.data.auth.DataRepository
import com.example.gymapp.domain.user.LoginCredentials
import com.example.gymapp.domain.user.Registeruser
import com.example.gymapp.domain.user.Session
import com.example.gymapp.ui.screens.login.LoginStatus

sealed class RegisterStatus {
    object Idle : RegisterStatus()
    object Success : RegisterStatus()
    object Failure : RegisterStatus()
}

class RegisterViewModel : ViewModel() {

    private val _status = MutableLiveData<RegisterStatus>()
    val status: MutableLiveData<RegisterStatus> = _status

    private val _session = MutableLiveData<Session?>()
    val session: MutableLiveData<Session?> = _session

    private val _name = MutableLiveData<String>("Santiago")
    val name: LiveData<String> = _name

    private val _lastName = MutableLiveData<String>("Moreno")
    val lastName: LiveData<String> = _lastName

    private val _email = MutableLiveData<String>("jampiersantiago@gmail.com")
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>("Github07")
    val password: LiveData<String> = _password

    private val _weight = MutableLiveData<String>("100")
    val weight: LiveData<String> = _weight

    private val _height = MutableLiveData<String>("1.80")
    val height: LiveData<String> = _height

    private val _rh = MutableLiveData<String>("A+")
    val rh: LiveData<String> = _rh

    private val _gender = MutableLiveData<String>("M")
    val gender: LiveData<String> = _gender

    private val _dateOfBirthDay = MutableLiveData<String>("2023-10-05")
    val dateOfBirthDay: LiveData<String> = _dateOfBirthDay

    private val _occupation = MutableLiveData<String>("Desarrollador de software")
    val occupation: LiveData<String> = _occupation

    private val _loginErrorMessage = MutableLiveData<String?>()
    val loginErrorMessage: MutableLiveData<String?> = _loginErrorMessage

    // Métodos para actualizar cada campo
    fun onNameChanged(newName: String) {
        _name.value = newName
    }

    fun onLastNameChanged(newLastName: String) {
        _lastName.value = newLastName
    }

    fun onEmailChanged(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword
    }

    fun onWeightChanged(newWeight: String) {
        _weight.value = newWeight
    }

    fun onHeightChanged(newHeight: String) {
        _height.value = newHeight
    }

    fun onRhChanged(newRh: String) {
        _rh.value = newRh
    }

    fun onGenderChanged(newGender: String) {
        _gender.value = newGender
    }

    fun onDateOfBirthChanged(newDateOfBirthDay: String) {
        _dateOfBirthDay.value = newDateOfBirthDay
    }

    fun onOccupationChanged(newOccupation: String) {
        _occupation.value = newOccupation
    }

    suspend fun saveUserData() {
        Log.d("RegisterViewModel", "Saving user data...")

        var response: Result<Session> = DataRepository().registerUser(
            Registeruser(
                name = _name.value,
                lastName = _lastName.value,
                email = _email.value,
                password = _password.value,
                weight = _weight.value,
                height = _height.value,
                rh = _rh.value,
                gender = _gender.value,
                dateOfBirthDay = _dateOfBirthDay.value,
                occupation = _occupation.value,
                lessions = true,
                lessionsDescription = "Ruptura de ligamento cruzado",
                rol = 2
            )
        )

        Log.d("RegisterStatus", "Status actualizado: ${response}")

        if (response.isSuccess) {
            var c  = response.getOrNull()
            Log.d("LoginViewModel", "Login successful: $c")

            _status.value = RegisterStatus.Success;
            _session.value = c
            _loginErrorMessage.value = null
        }else{
            var l = response.exceptionOrNull()?.message ?: "Unknown error"
            Log.d("LoginViewModel", "Error during login: $l")
            _status.value = RegisterStatus.Failure
            _loginErrorMessage.value = "Credenciales incorrectas"
        }

    }
}
