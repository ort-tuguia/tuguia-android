package ort.tp3_login.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ort.tp3_login.R
import ort.tp3_login.adapters.ServicioAdapter
import ort.tp3_login.adapters.ServicioGuiaAdapter
import ort.tp3_login.entities.ServicioCard
import ort.tp3_login.viewModels.ViewModelHomeTurista


class ReservaTurista : Fragment() {

    //ViewModel
    private val viewModel: ViewModelHomeTurista by activityViewModels()

    //RecyclerView
    lateinit var recyclerView: RecyclerView
    var cardsTuristaLista : MutableList<ServicioCard> = ArrayList<ServicioCard>()
    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var adapter: ServicioGuiaAdapter

    lateinit var view1 : View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view1 = inflater.inflate(R.layout.fragment_reserva_turista, container, false)
        recyclerView = view1.findViewById(R.id.recyclerViewReservasTurista)
        return view1
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.listaReservas.observe(viewLifecycleOwner, Observer{result ->
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
        viewModel.servicioItemSeleccionado = viewModel.actividades.value?.get(position)!!
        view1.findNavController().navigate(R.id.action_reservaTurista_to_detalleReservas)
        return true
    }


}