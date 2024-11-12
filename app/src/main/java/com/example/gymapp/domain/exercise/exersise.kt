package com.example.gymapp.domain.exercise

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable
data class Exersise (
    val id: Long? = null,
    val name: String? = null,
    val description: String? = null,
    val image: String? = null
)
