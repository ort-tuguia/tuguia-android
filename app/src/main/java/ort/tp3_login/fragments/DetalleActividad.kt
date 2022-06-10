package ort.tp3_login.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.google.gson.Gson
import ort.tp3_login.R
import ort.tp3_login.dataclasses.ServicioItem
import ort.tp3_login.dataclasses.UsuarioLogin
import ort.tp3_login.viewModels.ViewModelHomeTurista


class DetalleActividad : Fragment() {

    lateinit private var view1 : View
    lateinit private var valoracion : TextView
    lateinit var title: TextView
    lateinit var description: TextView
    private val viewModel: ViewModelHomeTurista by activityViewModels()
    lateinit var buttonReservar : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view1 =inflater.inflate(R.layout.fragment_detalle_actividad, container, false)
        valoracion = view1.findViewById(R.id.textviewValoracion)
        title = view1.findViewById(R.id.textviewTitle)
        description = view1.findViewById(R.id.textviewDescription)
        buttonReservar = view1.findViewById(R.id.buttonReservar)
        title.text = viewModel.servicioItemSeleccionado.name
        description.text = viewModel.servicioItemSeleccionado.description

        return view1
    }

    override fun onStart() {
        super.onStart()
        valoracion.setOnClickListener{
            view1.findNavController().navigate(R.id.action_detalleActividad_to_reviews)
        }
        buttonReservar.setOnClickListener{
            //TODO PEgarle a backend agregando a reservasGuia y a reservasTurista
            //TODO SACAR CONTACTO DE PANTALLA
            showDialog()
        }
    }
    private fun showDialog(){
        val dialog = AlertDialog.Builder(context)
        dialog.setTitle("Contacto")
        dialog.setMessage("Nombre del guia: "+viewModel.servicioItemSeleccionado.guideUsername
                +"\n"+"Telefono: "+viewModel.servicioItemSeleccionado.price
                +"\n"+"Correo: "+viewModel.servicioItemSeleccionado.name)
        dialog.setPositiveButton("Aceptar") { dialog, which ->}
        dialog.show()

    }


}