package ort.tp3_login.dataclasses

data class ServicioItem(
    val createdAt: String,
    val description: String,
    val distanceKm: Double,
    val guideUsername: String,
    val id: String,
    val locationLatitude: Double,
    val locationLongitude: Double,
    val name: String,
    val price: Int,
    val updatedAt: String,
    val category: CategoriaItem
)