package ort.tp3_login.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ort.tp3_login.R
import ort.tp3_login.adapters.ServicioAdapter
import ort.tp3_login.entities.ServicioCard
import ort.tp3_login.viewModels.ViewModelHomeTurista

class favoritos_turista: Fragment() {
    lateinit var v: View
    private val viewModel: ViewModelHomeTurista by activityViewModels()
    lateinit var recyclerView: RecyclerView
    var cardsTuristaLista : MutableList<ServicioCard> = ArrayList<ServicioCard>()
    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var adapter: ServicioAdapter
    //var favoritos = MutableLiveData<MutableList<ServicioCard>>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_favoritos_turista, container, false)
        recyclerView = v.findViewById(R.id.recyclerViewFavoritos)
        viewModel.favoritos.value = ArrayList<ServicioCard>()
        loadActivitiesFavorites()
        viewModel.favoritos.observe(viewLifecycleOwner, Observer { result ->
            cardsTuristaLista = result
            recyclerView.hasFixedSize()
            linearLayoutManager = LinearLayoutManager(context)
            recyclerView.layoutManager = linearLayoutManager
            adapter = ServicioAdapter(cardsTuristaLista){x ->
                // onItemClick(x)
            }
            recyclerView.adapter = adapter
        })
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//
//        loadActivitiesFavorites ()
//        favoritos.observe(viewLifecycleOwner, Observer { result ->
//            cardsTuristaLista = result
//            recyclerView.hasFixedSize()
//            linearLayoutManager = LinearLayoutManager(context)
//            recyclerView.layoutManager = linearLayoutManager
//            adapter = ServicioAdapter(cardsTuristaLista){x ->
//               // onItemClick(x)
//            }
//            recyclerView.adapter = adapter
//        })

    }
    override fun onStart() {
        super.onStart()

    }


    fun loadActivitiesFavorites () {
        // fetch data de la API

        viewModel.user.observe(viewLifecycleOwner, Observer { result ->
        result.favActivities?.forEach() {
            var urlPhoto: Uri = "".toUri()
            if (it.photos.isNotEmpty()) {
                urlPhoto = it.photos[0].photoUrl.toUri()
            }
            viewModel.favoritos.value?.add(
                ServicioCard(
                    it.guideUsername,
                    it.name,
                    R.drawable.icon_profile,
                    5,
                    urlPhoto,
                    "categoria",
                    it.id,
                    viewModel.user.value,
                    viewModel.token
                )
            )
        }
        })
    }


}