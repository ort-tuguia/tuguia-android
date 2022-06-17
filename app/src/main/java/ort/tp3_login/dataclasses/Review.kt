package ort.tp3_login.dataclasses

data class Review(
    val id: String,
    val comment: String,
    val score: Double,
    val createdAt: String,
    val updatedAt: String
)
