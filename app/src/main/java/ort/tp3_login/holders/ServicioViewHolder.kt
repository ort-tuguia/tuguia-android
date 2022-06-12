package ort.tp3_login.holders

import android.net.Uri
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import ort.tp3_login.R

class ServicioViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    private var view : View
    init {
        this.view = v
    }

    fun setNames (titulo: String, nombreGuia: String, imageView: Uri, valoracion: Int, perfilpic: Int) {
        var textViewTitulo : TextView = view.findViewById(R.id.tituloCard)
        var textViewNombreGuia : TextView = view.findViewById(R.id.nombreGuiaCard)
        var imagenView : ImageView = view.findViewById(R.id.imageCard)
        var textViewValoracion : TextView = view.findViewById(R.id.valoracionCard)
        var perfilPic : CircleImageView = view.findViewById(R.id.perfilPicCard)
        var imageFavorite : ImageView = view.findViewById(R.id.imageFavorite)


        textViewTitulo.text = titulo
        textViewNombreGuia.text = nombreGuia
        //imagenView.setImageResource(imageView)
        textViewValoracion.text = valoracion.toString()


        perfilPic.setImageResource(perfilpic)

        if(imageView != "".toUri()) {
            Picasso.get().load(imageView).into(imagenView)
        }else{
            imagenView.setImageResource(R.drawable.no_image_available)
        }

        imageFavorite.setOnClickListener{
                imageFavorite.setImageResource(R.drawable.icon_favorites_red)
        //TODO Sacar de favoritos
//       TODO Pegarle al backend
}





        }



    fun getCardLayout () : CardView {
        return view.findViewById(R.id.card_package_item)
    }
}