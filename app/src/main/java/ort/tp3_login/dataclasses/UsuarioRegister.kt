package ort.tp3_login.dataclasses



data class UsuarioRegister(
    var username: String,
    var firstName: String,
    var lastName: String,
    var email: String,
    var password: String,
    var guideIdentification: String,
    var isGuide: Boolean
)