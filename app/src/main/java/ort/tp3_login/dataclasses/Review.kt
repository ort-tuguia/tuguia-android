package ort.tp3_login.dataclasses

data class Review(
    val id: String,
    val commentary: String,
    val score: Double,
    val activityId: String,
    val touristUsername: String,
    val createdAt: String,
    val updatedAt: String
)
