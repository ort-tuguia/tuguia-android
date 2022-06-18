package ort.tp3_login.viewModels

import android.icu.text.DateFormat
import android.icu.text.SimpleDateFormat
import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ort.tp3_login.R
import ort.tp3_login.dataclasses.*
import ort.tp3_login.entities.ReviewCard
import ort.tp3_login.entities.ServicioCard
import java.util.*
import kotlin.collections.ArrayList


class ViewModelHomeTurista : ViewModel() {
    var lista = MutableLiveData<MutableList<ServicioCard>>()
    var listaReservas = MutableLiveData<MutableList<ServicioCard>>()
    var listaReviews = MutableLiveData<MutableList<ReviewCard>>()

    var user = MutableLiveData<UsuarioLogin>()

    var actividades = MutableLiveData<MutableList<ServicioItem>>()
    var reservas = MutableLiveData<MutableList<Reserva>>()
    var reviews = MutableLiveData<MutableList<Review>>()

    var token :String = ""
    var myLatitude : Double = 0.0
    var myLongitude : Double = 0.0
    lateinit var categorias: MutableListIterator<CategoriaItem>
    var selectedCategorie: ArrayList<Boolean> = ArrayList<Boolean>()

    lateinit var servicioItemSeleccionado: ServicioItem
    lateinit var reservaSeleccionado : Reserva
    var favoritos = MutableLiveData<MutableList<ServicioCard>>()




    init {
       // initializar()
   }


    fun loadActivities () {
        // fetch data de la API
        lista.value = ArrayList<ServicioCard>()
        actividades.value?.forEach() {
            var urlPhoto: Uri = "".toUri()
            if(it.photos.isNotEmpty()){
                urlPhoto =  it.photos[0].photoUrl.toUri()
            }
            lista.value?.add(
                ServicioCard(it.guideUsername,
                    it.name,
                    R.drawable.icon_profile,
                    it.reviews?.avgScore?:0.0,
                    urlPhoto,
                    "categoria",
                    it.id,
                    user.value,
                    token)
            )
        }
/*


        lista.value?.add(
            ServicioCard("Tom Maenhout",
                "24/10/2022",
                "free walking tour Missiones",
                R.drawable.profile,
                5,
                R.drawable.recycler_city,
                "city trip")
        )

        lista.value?.add(
            ServicioCard("Tommy",
                "13/10/2022",
                "Cataratas de Iguazu",
                R.drawable.icon_profile,
                4,
                R.drawable.recycler_iguazu,
                "naturaleza")
        )*/
    }

    fun loadReservas () {
        listaReservas.value = ArrayList<ServicioCard>()
        reservas.value?.forEach() {
            var urlPhoto: Uri = "".toUri()
            if (it.activity.photos.isNotEmpty()) {
                urlPhoto = it.activity.photos[0].photoUrl.toUri()
            }
            listaReservas.value?.add(
                ServicioCard(
                    it.activity.guideUsername,
                    it.activity.name,
                    R.drawable.icon_profile,
                    it.review?.score?:0.0,
                    urlPhoto,
                    "categoria",
                    it.id,
                    user.value,
                    token
                )
            )
        }
    }

    fun loadReviews () {
        listaReviews.value = ArrayList<ReviewCard>()
        reviews.value?.forEach() {
            var fecha: DateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm")
            var inputFormat: DateFormat =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
            var date : Date = inputFormat.parse(it.createdAt)
            listaReviews.value?.add(
                ReviewCard(
                    fecha.format(date),
                    it.comment,
                    it.score
                )
            )
        }
    }

    // calcular distancia entre dos coordinatos
    fun distanceInKm(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val theta = lon1 - lon2
        var dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta))
        dist = Math.acos(dist)
        dist = rad2deg(dist)
        dist = dist * 60 * 1.1515
        dist = dist * 1.609344
        return dist
    }

    private fun deg2rad(deg: Double): Double {
        return deg * Math.PI / 180.0
    }

    private fun rad2deg(rad: Double): Double {
        return rad * 180.0 / Math.PI
    }

    //fun buscar
}