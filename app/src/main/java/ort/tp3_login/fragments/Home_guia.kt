package ort.tp3_login.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
        botonAgregar = view1.findViewById(R.id.buttonAgregar)
        recyclerView = view1.findViewById(R.id.recyclerViewHomeGuia)



        //var nombreDeUsuario = requireArguments().getString("usuario")
        //textHola.text = "Hola... ${nombreDeUsuario.toString()}"
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
        })

    }

    override fun onStart() {
        super.onStart()
        botonAgregar.setOnClickListener {
            openDialog()
        }

    }
    private fun openDialog() {
        var alertDialog = AlertDialog.Builder(this.context)
        alertDialog.setTitle("Locación")
            .setMessage("Eliga la ubicación de la actividad en el mapa")
            .setIcon(R.drawable.icon_agregar_ubicacion)
            .setCancelable(false)
            .setPositiveButton("Ok",
                DialogInterface.OnClickListener{ dialog: DialogInterface?, which: Int ->
                    view1.findNavController().navigate(R.id.action_home_guia_to_mapsAgregarServicio)
                })
        alertDialog.create().show()
    }

    private fun onItemClick(position: Int) : Boolean {
        viewModel.servicioItemSeleccionado = viewModel.actividades.value?.get(position)!!
        view1.findNavController().navigate(R.id.action_home_guia_to_detalleActividadGuia)
        return true
    }



}