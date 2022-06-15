package ort.tp3_login.holders

import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.runBlocking
import ort.tp3_login.R
import ort.tp3_login.dataclasses.ServicioService
import ort.tp3_login.dataclasses.UsuarioLogin
import ort.tp3_login.services.RetrofitInstance

class ServicioViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    private var view : View
    private var servicioService : ServicioService = RetrofitInstance
        .getRetrofitInstance()
        .create(ServicioService::class.java)




    init {
        this.view = v
    }

    fun setNames (titulo: String, nombreGuia: String, imageView: Uri, valoracion: Int, perfilpic: Int, activityId: String,user:UsuarioLogin, token:String) {
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
        fun checkFavorite(activityId: String):Boolean {
            var flag :Boolean = false

            user.favActivities?.forEach { activity ->
                if (activity.id == activityId) {
                    flag = true
                }
            }
            return flag
        }
        if(checkFavorite(activityId)) {
            imageFavorite.setImageResource(R.drawable.icon_favorites_red)
        }else{
            imageFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
        imageFavorite.setOnClickListener{
                if(checkFavorite(activityId)){
                    imageFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)

                    fun deleteFavActivity() = runBlocking(CoroutineName("deleteFavActivity")) {
                        var response = servicioService.deleteFavActivity(activityId,token)
                        user.favActivities= response.body()!!
                    }
                    deleteFavActivity()
                }else{
                    imageFavorite.setImageResource(R.drawable.icon_favorites_red)
                    fun postFavActivity() = runBlocking(CoroutineName("postFavActivity")) {
                        var response = servicioService.postFavActivity(activityId,token)
                        user.favActivities= response.body()!!
                    }
                    postFavActivity()
                }
}
        }





    fun getCardLayout () : CardView {
        return view.findViewById(R.id.cardViewReview)
    }
}