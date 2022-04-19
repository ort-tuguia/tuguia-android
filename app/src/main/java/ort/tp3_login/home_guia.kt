package ort.tp3_login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController


class home_guia : Fragment() {
    lateinit var textViewVolver : TextView
    lateinit var view1 : View
    lateinit var textHola : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        view1 = inflater.inflate(R.layout.fragment_home_guia, container, false)
        textViewVolver = view1.findViewById(R.id.textViewVolverGuia)

        textHola = view1.findViewById(R.id.textViewHola)

        var nombreDeUsuario = requireArguments().getString("usuario")
        textHola.text = "Hola... ${nombreDeUsuario.toString()}"
        return view1
    }

    override fun onStart() {
        super.onStart()

        textViewVolver.setOnClickListener {
            val action1 = home_guiaDirections.actionHomeGuiaToLogin()
            view1.findNavController().navigate(action1)
        }
    }



}