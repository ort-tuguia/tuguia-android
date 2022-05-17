package ort.tp3_login.entities

class ServicioCard
                    (nombreGuia : String?,
                    fecha : String?,
                    titulo : String?,
                    profilePic: Int?,
                    valoracion: Int?,
                    imagen: Int?,
                    categoria: String?
) {

    var nombreGuia : String
    var fecha : String
    var titulo : String
    var profilePic : Int
    var valoracion : Int
    var imagen : Int
    var categoria : String


    init {
        this.nombreGuia = nombreGuia!!
        this.fecha = fecha!!
        this.titulo = titulo!!
        this.profilePic = profilePic!!
        this.valoracion = valoracion!!
        this.imagen = imagen!!
        this.categoria = categoria!!

    }
}