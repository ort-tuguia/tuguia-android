package ort.tp3_login.dataclasses

data class CrearServicio(
    val name: String,
    val description: String,
    val locationLatitude: Double,
    val locationLongitude: Double,
    val price: Double,
    val photos: List<Photo>,
    val categoryId: String,
    val guideUsername: String

)
