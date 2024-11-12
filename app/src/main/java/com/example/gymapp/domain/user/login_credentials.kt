package com.example.gymapp.domain.user

import kotlinx.serialization.Serializable


@Serializable
data class LoginCredentials(
    val email: String,
    val password: String
)