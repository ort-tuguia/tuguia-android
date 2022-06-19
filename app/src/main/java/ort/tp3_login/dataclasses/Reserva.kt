package ort.tp3_login.dataclasses

data class Reserva(
    val id: String,
    val tourist : UsuarioLogin,
    val activity : ServicioItem,
    val review : Review?,
    val createdAt: String,
    val guide : UsuarioLogin,)
