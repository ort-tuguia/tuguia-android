package ort.tp3_login.viewModels

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ort.tp3_login.R
import ort.tp3_login.dataclasses.Review
import ort.tp3_login.dataclasses.ServicioItem
import ort.tp3_login.dataclasses.UsuarioLogin
import ort.tp3_login.entities.ServicioCard

class ViewModelGuia: ViewModel() {
    var user = MutableLiveData<UsuarioLogin>()
    var token :String = ""
    var actividades = MutableLiveData<MutableList<ServicioItem>>()
    var lista = MutableLiveData<MutableList<ServicioCard>>()
    var listaReviews = MutableLiveData<MutableList<Review>>()
    //var valoracion : Double = calcularValoracion()


    // agregarServicio
    //var servicioName: String = ""
    var servicioCategoriaId : String = ""
    //var servicioDescription: String = ""
    var servicioLocationlat: Double = 0.0
    var servicioLocationlon: Double = 0.0
    //var servicioPrice: Double= 0.0
    var servicioUrlFoto: Uri? = null
    var  categorias: Array<CharSequence>? = null

    //detalleServicio
    var servicioItemSeleccionado: ServicioItem? = null



//    fun calcularValoracion() : Double{
//        var valoracion : Double = 0.0
//        var size : Int? = actividades.value?.size
//        if (actividades.value != null)
//        actividades.value?.forEach {
//            //valoracion += it.valoracion
//        }
//        if (size != null) {
//            valoracion /= size
//        }
//        return valoracion
//    }


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
                    5.0,
                    urlPhoto,
                    "categoria",
                    it.id,
                    user.value,
                    token)
            )
        }
    }







}