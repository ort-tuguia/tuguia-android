package ort.tp3_login.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import ort.tp3_login.R


class RegistroGuia : Fragment() {

    lateinit var view1 : View
    lateinit var buttonVolver : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view1 = inflater.inflate(R.layout.fragment_registro_guia, container, false)
        buttonVolver = view1.findViewById(R.id.textViewVolverRegistro)
        return view1
    }

    override fun onStart() {
        super.onStart()
        buttonVolver.setOnClickListener {
            val action = RegistroGuiaDirections.actionRegistroGuiaToRegistroEligir()
            view1.findNavController().navigate(action)
        }
    }


}