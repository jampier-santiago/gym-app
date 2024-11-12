package com.example.gymapp.domain.user



import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable
data class Registeruser (
    val name: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val password: String? = null,
    val weight: String? = null,
    val height: String? = null,

    @SerialName("RH")
    val rh: String? = null,

    val gender: String? = null,
    val dateOfBirthDay: String? = null,
    val occupation: String? = null,
    val lessions: Boolean? = null,
    val lessionsDescription: String? = null,
    val rol: Long? = null
)
