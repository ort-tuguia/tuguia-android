package ort.tp3_login.dataclasses

data class ServiciosSearch (
    val currentLatitude: Double,
    val currentLongitude: Double,
    val maxKm: Double,
    val maxResults: Int,
    val categoriesIds: List<String>
        )
