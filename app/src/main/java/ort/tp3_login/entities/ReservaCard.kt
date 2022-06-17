package ort.tp3_login.entities


class ReservaCard
    (
     nombreActividad : String?,
     nombreUsuario: String?,
     telefono: String?,
     email : String?,

) {

    var nombreActividad : String
    var nombreUsuario : String
    var telefono : String
    var email : String



    init {
        this.nombreActividad = nombreActividad!!
        this.nombreUsuario = nombreUsuario!!
        this.telefono = telefono!!
        this.email = email!!


    }
}