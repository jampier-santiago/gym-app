package com.example.gymapp.domain.user

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable
data class Session (
    val user: User? = null,
    val token: String? = null
)