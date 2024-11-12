package com.example.gymapp.domain.user

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable
data class User (
    val id: Int? = null,
    val name: String? = null,

    @SerialName("last_name")
    val lastName: String? = null,

    val email: String? = null,
    val years: Long? = null,
    val gender: String? = null,
    val rol: Rol? = null
)