package ort.tp3_login.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import ort.tp3_login.R


class home_guia : Fragment() {

    lateinit var view1 : View
    lateinit var botonAgregar: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        view1 = inflater.inflate(R.layout.fragment_home_guia, container, false)
        botonAgregar = view1.findViewById(R.id.buttonAgregar)



        //var nombreDeUsuario = requireArguments().getString("usuario")
        //textHola.text = "Hola... ${nombreDeUsuario.toString()}"
        return view1
    }

    override fun onStart() {
        super.onStart()
        botonAgregar.setOnClickListener {
            view1.findNavController().navigate(R.id.action_home_guia_to_agregarServicio)
        }

    }



}