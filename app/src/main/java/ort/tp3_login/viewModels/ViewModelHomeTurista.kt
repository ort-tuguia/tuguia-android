package ort.tp3_login.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ort.tp3_login.R
import ort.tp3_login.entities.ServicioCard

class ViewModelHomeTurista : ViewModel() {
    val lista = MutableLiveData<MutableList<ServicioCard>>()

    init {
        lista.value = ArrayList<ServicioCard>()
        initializar()
    }

    fun initializar () {
        // fetch data de la API
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
        )
    }

    //fun buscar
}