package com.example.gymapp.domain.exercise

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable
data class Category (
    val id: Int? = null,
    val name: String? = null
)
