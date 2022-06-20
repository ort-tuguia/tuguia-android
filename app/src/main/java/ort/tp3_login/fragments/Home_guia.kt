package ort.tp3_login.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ort.tp3_login.R
import ort.tp3_login.adapters.ServicioAdapter
import ort.tp3_login.adapters.ServicioGuiaAdapter
import ort.tp3_login.entities.ServicioCard
import ort.tp3_login.viewModels.ViewModelGuia


class home_guia : Fragment() {

    lateinit var view1 : View
    lateinit var botonAgregar: Button
    lateinit var promedio : TextView

    //RecyclerView
    lateinit var recyclerView: RecyclerView
    var cardsGuia : MutableList<ServicioCard> = ArrayList<ServicioCard>()
    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var adapter: ServicioGuiaAdapter

    //Viewmodel
    private val viewModel: ViewModelGuia by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view1 = inflater.inflate(R.layout.fragment_home_guia, container, false)
        viewModel.servicioLocationlon = 0.0
        viewModel.servicioLocationlat = 0.0
        botonAgregar = view1.findViewById(R.id.buttonAgregar)
        recyclerView = view1.findViewById(R.id.recyclerViewHomeGuia)
        promedio = view1.findViewById(R.id.textViewHomeValoracion)
        viewModel.loadActivities()
        return view1
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.lista.observe(viewLifecycleOwner, Observer{result ->

            cardsGuia = result

            //configuraciòn obligatoria recyclerview
            recyclerView.hasFixedSize()
            linearLayoutManager = LinearLayoutManager(context)
            recyclerView.layoutManager = linearLayoutManager

            //setear adapter
            adapter = ServicioGuiaAdapter(cardsGuia){x ->
                onItemClick(x)
            }
            //asignar adaptar a recyclerview
            recyclerView.adapter = adapter
            viewModel.valoracion = viewModel.calcularValoracion()
            promedio.text = viewModel.valoracion.toString()

            //viewModel.loadActivities()

        })

    }

    override fun onStart() {
        super.onStart()
        viewModel.loadActivities()
        botonAgregar.setOnClickListener {
            view1.findNavController().navigate(R.id.action_home_guia_to_fotoAgregarServicio)
        }

    }

    private fun onItemClick(position: Int) : Boolean {
        Log.d("onItemClick", viewModel.actividades.value?.get(position)!!.toString())
        viewModel.servicioItemSeleccionado = viewModel.actividades.value?.get(position)!!
        view1.findNavController().navigate(R.id.action_home_guia_to_detalleActividadGuia)
        return true
    }



}