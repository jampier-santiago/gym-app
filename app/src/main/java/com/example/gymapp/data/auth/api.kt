package com.example.gymapp.data.auth

import android.content.ContentValues.TAG
import android.util.Log
import com.example.gymapp.domain.exercise.Category
import com.example.gymapp.domain.exercise.Exersise
import com.example.gymapp.domain.user.LoginCredentials
import com.example.gymapp.domain.user.Registeruser
import com.example.gymapp.domain.user.Rol
import com.example.gymapp.domain.user.Session
import com.example.gymapp.domain.user.User
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET

import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface DataAPI {

    @GET("users")
    suspend fun getUsers(): Response<List<User>>

    @GET("exercises")
    suspend fun getExercises(
        @Query("category") category: Int
    ): Response<List<Exersise>>

    @GET("category-exercises")
    suspend fun getCategories(): Response<List<Category>>

    @GET("rols")
    suspend fun getRoles(): Response<List<Rol>>

    @POST("rols")
    suspend fun createRole(@Body role: Rol): Response<Rol>

    @POST("users/login")
    suspend fun loginUser(@Body credentials: LoginCredentials): Response<Session>

    @POST("users/register")
    suspend fun registerUser(@Body credentials: Registeruser): Response<Session>

    @DELETE("users/{id}")
    suspend fun deleteUser(@Path("id") userId: Int): Response<Unit>


}

object RetrofitInstance {
    private val json = Json { ignoreUnknownKeys = true }

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.10.154:3000/api/")
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    val authAPI: DataAPI = retrofit.create(DataAPI::class.java)
}




class DataRepository {
    suspend fun fetchRoles(): Result<List<Rol>> {
        return try {
            val response = RetrofitInstance.authAPI.getRoles()
            if (response.isSuccessful) {
                Log.d(TAG, "Fetched ${response.body()} roles successfully")
                Result.success(response.body() ?: emptyList())
            } else {
                Log.d(TAG, "Error fetching roles: ${response.code()} ${response.message()}")
                Result.failure(Exception("Error: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Log.d(TAG, "Error fetching roles: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun createRole(role: Rol): Result<Rol> {
        return try {
            val response = RetrofitInstance.authAPI.createRole(role)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun loginUser(credentials: LoginCredentials): Result<Session> {
        return try {
            val response = RetrofitInstance.authAPI.loginUser(credentials)
            if (response.isSuccessful) {
                Log.d(TAG, "Login successful: ${response.body()}")
                Result.success(response.body()!!)
            } else {
                Log.d(TAG, "Login failed: ${response.code()} ${response.message()}")
                Result.failure(Exception("Login failed: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Log.d(TAG, "Error during login: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun loadUsers(): Result<List<User>> {
        return try {
            val response = RetrofitInstance.authAPI.getUsers()
            if (response.isSuccessful) {
                Log.d(TAG, "Users loaded: ${response.body()}")
                Result.success(response.body()!!)
            } else {
                Log.d(TAG, "Users failed: ${response.code()} ${response.message()}")
                Result.failure(Exception("Login failed: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Log.d(TAG, "Error during load users: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun registerUser(credentials: Registeruser): Result<Session> {
        return try {
            val response = RetrofitInstance.authAPI.registerUser(credentials)

            if (response.isSuccessful) {
                Log.d(TAG, "Registration successful: ${response.body()}")
                Result.success(response.body()!!)
            } else {
                Log.d(TAG, "Registration failed: ${response.code()} ${response.message()}")
                Result.failure(Exception("Registration failed: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Log.d(TAG, "Error during registration: ${e.message}")
            Result.failure(e)
        }


    }

    suspend fun loadCategories(): Result<List<Category>> {
        return try {
            val response = RetrofitInstance.authAPI.getCategories()

            if (response.isSuccessful) {
                Log.d(TAG, "loadCategories success: ${response.body()}")
                Result.success(response.body()!!)
            } else {
                Log.d(TAG, "loadCategories failed: ${response.code()} ${response.message()}")
                Result.failure(Exception("loadCategories failed: ${response.code()} ${response.message()}"))
            }

        } catch (e: Exception) {
            Log.d(TAG, "Error during load users: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun loadExercises(id: Int): Result<List<Exersise>> {
        return try {
            val response = RetrofitInstance.authAPI.getExercises(category = id)
            if (response.isSuccessful) {
                Log.d(TAG, "loadExercises success: ${response.body()}")
                Result.success(response.body()!!)
            } else {
                Log.d(TAG, "loadExercises failed: ${response.code()} ${response.message()}")
                Result.failure(Exception("loadExercises failed: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Log.d("TAG", "Error during load exercises: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun deleteUser(id: Int): Result<Unit> {
        Log.d(TAG, "deleteUser: $id")

        return try {
            val response = RetrofitInstance.authAPI.deleteUser(id)

            if (response.isSuccessful) {
                Log.d(TAG, "deleteUser success: ${response.body()}")
                Result.success(Unit)
            }else {
                Log.d(TAG, "deleteUser failed: ${response.code()} ${response.message()}")
                Result.failure(Exception("deleteUser failed: ${response.code()} ${response.message()}"))
            }


        } catch (e: Exception) {
            Log.d("TAG", "Error during load exercises: ${e.message}")
            Result.failure(e)
        }
    }

}



