package ort.tp3_login.entities

import android.net.Uri
import ort.tp3_login.dataclasses.UsuarioLogin

class ServicioCard
                    (nombreGuia : String?,
                    titulo : String?,
                    profilePic: Int?,
                    valoracion: Int?,
                    imagen: Uri?,
                    categoria: String?,
                     activityId: String?,
                     user: UsuarioLogin?,
                     token: String?
) {

    var nombreGuia : String
    var titulo : String
    var profilePic : Int
    var valoracion : Int
    var imagen : Uri
    var categoria : String
    var activityId : String
    var user: UsuarioLogin
    var token: String


    init {
        this.nombreGuia = nombreGuia!!
        this.titulo = titulo!!
        this.profilePic = profilePic!!
        this.valoracion = valoracion!!
        this.imagen = imagen!!
        this.categoria = categoria!!
        this.activityId = activityId!!
        this.user = user!!
        this.token = token!!

    }
}