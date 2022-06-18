package ort.tp3_login.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import ort.tp3_login.R
import ort.tp3_login.adapters.ServicioAdapter
import ort.tp3_login.adapters.ServicioGuiaAdapter
import ort.tp3_login.dataclasses.Reserva
import ort.tp3_login.dataclasses.ServicioService
import ort.tp3_login.dataclasses.Servicios
import ort.tp3_login.dataclasses.ServiciosSearch
import ort.tp3_login.entities.ServicioCard
import ort.tp3_login.services.RetrofitInstance
import ort.tp3_login.viewModels.ViewModelHomeTurista
import retrofit2.Response


class ReservaTurista : Fragment() {

    //ViewModel
    private val viewModel: ViewModelHomeTurista by activityViewModels()

    //RecyclerView
    lateinit var recyclerView: RecyclerView
    var cardsTuristaLista : MutableList<ServicioCard> = ArrayList()
    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var adapter: ServicioGuiaAdapter

    lateinit var view1 : View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view1 = inflater.inflate(R.layout.fragment_reserva_turista, container, false)
        recyclerView = view1.findViewById(R.id.recyclerViewReservasTurista)
        getReservas()
        return view1
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.listaReservas.observe(viewLifecycleOwner, Observer{result ->

            Log.d("result --> observer", result.toString())
            cardsTuristaLista = result

            //configuraciòn obligatoria recyclerview
            recyclerView.hasFixedSize()
            linearLayoutManager = LinearLayoutManager(context)
            recyclerView.layoutManager = linearLayoutManager

            //setear adapter
            adapter = ServicioGuiaAdapter(cardsTuristaLista){x ->
                    onItemClick(x)
            }
            //asignar adaptar a recyclerview
            recyclerView.adapter = adapter
        })


    }

    private fun onItemClick(position: Int) : Boolean {
        viewModel.reservaSeleccionado = viewModel.reservas.value?.get(position)!!
        view1.findNavController().navigate(R.id.action_reservaTurista_to_detalleReservas)
        return true
    }

    override fun onStart() {
        super.onStart()

        viewModel.loadReservas()
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