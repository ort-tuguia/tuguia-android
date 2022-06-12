package ort.tp3_login.entities

import android.net.Uri

class ServicioCard
                    (nombreGuia : String?,
                    titulo : String?,
                    profilePic: Int?,
                    valoracion: Int?,
                    imagen: Uri?,
                    categoria: String?
) {

    var nombreGuia : String
    var titulo : String
    var profilePic : Int
    var valoracion : Int
    var imagen : Uri
    var categoria : String


    init {
        this.nombreGuia = nombreGuia!!
        this.titulo = titulo!!
        this.profilePic = profilePic!!
        this.valoracion = valoracion!!
        this.imagen = imagen!!
        this.categoria = categoria!!

    }
}