package com.example.gymapp.ui.screens.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymapp.data.auth.DataRepository
import com.example.gymapp.domain.exercise.Category
import com.example.gymapp.domain.exercise.Exersise
import kotlinx.coroutines.launch

class UserViewModel: ViewModel() {



    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _categories = MutableLiveData<List<Category>>()
    val category: LiveData<List<Category>> = _categories

    private val _excercises = MutableLiveData<List<Exersise>>()
    val excercises: LiveData<List<Exersise>> = _excercises

    fun onLoadCategories() {
        viewModelScope.launch {
            _isLoading.value = true

            val response: Result<List<Category>> = DataRepository().loadCategories()

            if (response.isSuccess) {
                val c = response.getOrNull()
                Log.d("AdminViewModel", "Users loaded successfully: $c")
                _categories.value = c ?: emptyList()
            } else {
                val error = response.exceptionOrNull()?.message ?: "Unknown error"
                Log.d("AdminViewModel", "Error loading users: $error")
            }

            _isLoading.value = false
        }
    }

    fun onLoadExcercises(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true

            val response: Result<List<Exersise>> = DataRepository().loadExercises(id)

            if (response.isSuccess) {
                val c = response.getOrNull()
                Log.d("UserViewModel", "onLoadExcercises loaded successfully: $c")
                _excercises.value = c ?: emptyList()
            } else {
                val error = response.exceptionOrNull()?.message ?: "Unknown error"
                Log.d("UserViewModel", "Error onLoadExcercises: $error")
            }

            _isLoading.value = false
        }
    }

}