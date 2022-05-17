package ort.tp3_login.holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView
import ort.tp3_login.R

class ServicioViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    private var view : View
    init {
        this.view = v
    }

    fun setNames (titulo: String, nombreGuia: String, imageView: Int, valoracion: Int, perfilpic: Int,fecha: String) {
        var textViewTitulo : TextView = view.findViewById(R.id.tituloCard)
        var textViewNombreGuia : TextView = view.findViewById(R.id.nombreGuiaCard)
        var imagenView : ImageView = view.findViewById(R.id.imageCard)
        var textViewValoracion : TextView = view.findViewById(R.id.valoracionCard)
        var perfilPic : CircleImageView = view.findViewById(R.id.perfilPicCard)
        var fechaTextView : TextView = view.findViewById(R.id.fechaCard)

        textViewTitulo.text = titulo
        textViewNombreGuia.text = nombreGuia
        imagenView.setImageResource(imageView)
        textViewValoracion.text = valoracion.toString()
        perfilPic.setImageResource(perfilpic)
        fechaTextView.text = fecha
    }

    fun getCardLayout () : CardView {
        return view.findViewById(R.id.card_package_item)
    }
}