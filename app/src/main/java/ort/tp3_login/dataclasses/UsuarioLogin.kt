package ort.tp3_login.dataclasses

    data class UsuarioLogin(
        var username: String,
        var firstName: String,
        var lastName: String,
        var email: String,
        var role: String,
        var phones : List<Phone>,
        var guideIdentification: String,
        var favCategories : List<CategoriaItem>,
        var photoUrl:String
    )

