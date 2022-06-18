package ort.tp3_login.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ort.tp3_login.R
import ort.tp3_login.adapters.ReservasAdapter
import ort.tp3_login.adapters.ServicioGuiaAdapter
import ort.tp3_login.dataclasses.Reserva
import ort.tp3_login.dataclasses.ServicioService
import ort.tp3_login.entities.ReservaCard
import ort.tp3_login.entities.ServicioCard
import ort.tp3_login.services.RetrofitInstance
import ort.tp3_login.viewModels.ViewModelGuia
import ort.tp3_login.viewModels.ViewModelHomeTurista
import retrofit2.Response

class ReservasGuia : Fragment() {
    //ViewModel
    private val viewModel: ViewModelGuia by activityViewModels()

    //RecyclerView
    lateinit var recyclerView: RecyclerView
    var cardsReservaLista : MutableList<ReservaCard> = ArrayList()
    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var adapter: ReservasAdapter

    lateinit var view1 : View


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view1 =  inflater.inflate(R.layout.fragment_reservas_guia, container, false)
        recyclerView = view1.findViewById(R.id.recyclerViewReservasGuia)
        getReservas()
        return view1
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.listaReservas.observe(viewLifecycleOwner, Observer{result ->

            Log.d("result --> observer", result.toString())
            cardsReservaLista = result

            //configuraciòn obligatoria recyclerview
            recyclerView.hasFixedSize()
            linearLayoutManager = LinearLayoutManager(context)
            recyclerView.layoutManager = linearLayoutManager

            //setear adapter
            adapter = ReservasAdapter(cardsReservaLista){x ->
                //onItemClick(x)
            }
            //asignar adaptar a recyclerview
            recyclerView.adapter = adapter
        })


    }
    private fun onItemClick(position: Int) : Boolean {
        viewModel.servicioItemSeleccionado = viewModel.actividades.value?.get(position)!!
        view1.findNavController().navigate(R.id.action_reservaTurista_to_detalleReservas)
        return true
    }

    private fun getReservas() {
        val retService: ServicioService = RetrofitInstance
            .getRetrofitInstance()
            .create(ServicioService::class.java)
        val responseLiveData: LiveData<Response<List<Reserva>>> = liveData {
            val response = retService.getReservas(viewModel.token)

            emit(response)
        }

        responseLiveData.observe(viewLifecycleOwner,Observer {
            val reservasList = it.body()
            if (reservasList != null) {

                viewModel.reservas.value = reservasList as MutableList<Reserva>?
                Log.d("response -->Servicios", viewModel.reservas.value.toString())

            }
            viewModel.loadReservas()
        })

    }


}