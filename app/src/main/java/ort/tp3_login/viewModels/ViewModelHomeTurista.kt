package ort.tp3_login.viewModels

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ort.tp3_login.R
import ort.tp3_login.dataclasses.ServicioItem
import ort.tp3_login.dataclasses.UsuarioLogin
import ort.tp3_login.entities.ServicioCard


class ViewModelHomeTurista : ViewModel() {
    var lista = MutableLiveData<MutableList<ServicioCard>>()
    var user = MutableLiveData<UsuarioLogin>()
    var actividades = MutableLiveData<MutableList<ServicioItem>>()

    init {
        lista.value = ArrayList<ServicioCard>()
       // initializar()
   }


    fun loadActivities () {
        // fetch data de la API
        actividades.value?.forEach() {
            lista.value?.add(
                ServicioCard(it.guideUsername,

                    it.name,
                    R.drawable.profile,
                    5,
                    R.drawable.recycler_city,
                    "city trip")
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