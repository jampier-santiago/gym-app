package com.example.gymapp.ui.screens.admin

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymapp.data.auth.DataRepository
import com.example.gymapp.domain.exercise.Exersise
import com.example.gymapp.domain.user.Session
import com.example.gymapp.domain.user.User
import com.example.gymapp.ui.screens.login.LoginStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AdminViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    fun onLoadUsers() {
        viewModelScope.launch {
            _isLoading.value = true

            val response: Result<List<User>> = DataRepository().loadUsers()

            if (response.isSuccess) {
                val c = response.getOrNull()
                Log.d("AdminViewModel", "Users loaded successfully: $c")
                _users.value = c ?: emptyList()
            } else {
                val error = response.exceptionOrNull()?.message ?: "Unknown error"
                Log.d("AdminViewModel", "Error loading users: $error")
            }

            _isLoading.value = false
        }
    }

    fun onDelete(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true

            val response: Result<Unit> = DataRepository().deleteUser(id)

            if (response.isSuccess) {
                Log.d("AdminViewModel", "User deleted successfully")

                onLoadUsers()
            }else{
                val error = response.exceptionOrNull()?.message ?: "Unknown error"
                Log.d("AdminViewModel", "Error deleting user: $error")
            }

            _isLoading.value = false
        }
    }





}