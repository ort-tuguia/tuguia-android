package ort.tp3_login.entities

import android.net.Uri
import ort.tp3_login.dataclasses.UsuarioLogin

class ReviewCard(
    fecha: String?,
    review: String?,
    valoracion: Double?
) {

    var fecha : String
    var review : String
    var valoracion : Double



    init {
        this.fecha = fecha!!
        this.review = review!!
        this.valoracion = valoracion!!
    }
}