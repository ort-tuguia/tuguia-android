package ort.tp3_login.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import org.w3c.dom.Text
import ort.tp3_login.R
import ort.tp3_login.viewModels.ViewModelGuia
import ort.tp3_login.viewModels.ViewModelHomeTurista


class DetalleReservas : Fragment() {
    lateinit var title: TextView
    lateinit var description: TextView
    private val viewModel: ViewModelHomeTurista by activityViewModels()
    lateinit var buttonValorar : Button
    lateinit var nombreGuia : TextView
    lateinit var email : TextView
    lateinit var telefono : TextView
    lateinit var score : TextView

    lateinit var view1 : View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view1 = inflater.inflate(R.layout.fragment_detalle_reservas, container, false)
        title = view1.findViewById(R.id.reservasTitle)
        description = view1.findViewById(R.id.reservasDescripcion)
        buttonValorar = view1.findViewById(R.id.buttonValorar)
        nombreGuia = view1.findViewById(R.id.reservasNombreGuia)
        email = view1.findViewById(R.id.reservasEmail)
        telefono = view1.findViewById(R.id.reservasTelefono)
        score = view1.findViewById(R.id.textViewValorar)


        title.text = viewModel.reservaSeleccionado?.activity?.name
        description.text = viewModel.reservaSeleccionado?.activity?.description
        //score.text = viewModel.reservaSeleccionado.activity.reviews.avgScore.toString()
        score.visibility = View.GONE

        //TODO !!!!!
        nombreGuia.text = "Guia: "+ viewModel.reservaSeleccionado?.activity?.guideUsername
        //email.text = viewModel.servicioItemSeleccionado?.description
        //telefono.text = viewModel.servicioItemSeleccionado?.

        return view1
    }

    override fun onStart() {
        super.onStart()
        buttonValorar.setOnClickListener {
            view1.findNavController().navigate(R.id.action_detalleReservas_to_crearReview)
        }
    }


}