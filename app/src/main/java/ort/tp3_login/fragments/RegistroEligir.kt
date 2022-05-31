package ort.tp3_login.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_registro_eligir.view.*
import ort.tp3_login.R


class RegistroEligir : Fragment() {
    lateinit var view1 : View
    lateinit var buttonToGuia: Button
    lateinit var buttonToTurista: Button
    lateinit var textViewVolver: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view1 = inflater.inflate(R.layout.fragment_registro_eligir, container, false)
        buttonToGuia = view1.findViewById(R.id.buttonToGuia)
        buttonToTurista = view1.findViewById(R.id.buttonToTurista)
        textViewVolver = view1.findViewById(R.id.textViewVolverRegistro)
        return view1
    }

    override fun onStart() {
        super.onStart()
        buttonToGuia.setOnClickListener {
            val action = RegistroEligirDirections.actionRegistroEligirToRegistroGuia()
            view1.findNavController().navigate(action)
        }
        buttonToTurista.setOnClickListener {
            val action = RegistroEligirDirections.actionRegistroEligirToRegistroTurista()
            view1.findNavController().navigate(action)
        }
        textViewVolver.setOnClickListener{
            val action = RegistroEligirDirections.actionRegistroEligirToLogin()
            view1.findNavController().navigate(action)
        }
    }
}