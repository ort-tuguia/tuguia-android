package ort.tp3_login.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import ort.tp3_login.R


class DetalleActividad : Fragment() {

    lateinit private var view1 : View
    lateinit private var valoracion : TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {
        view1 =inflater.inflate(R.layout.fragment_detalle_actividad, container, false)
        valoracion = view1.findViewById(R.id.textviewValoracion)
        return view1
    }

    override fun onStart() {
        super.onStart()
        valoracion.setOnClickListener{
            view1.findNavController().navigate(R.id.action_detalleActividad_to_reviews)
        }
    }


}