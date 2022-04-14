package ort.tp3_login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController

class register : Fragment() {

    lateinit var view1 : View
    lateinit var textViewVolver : TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view1 = inflater.inflate(R.layout.fragment_register, container, false)
        textViewVolver = view1.findViewById(R.id.textViewVolverRegistro)
        return view1
    }

    override fun onStart() {
        super.onStart()

        textViewVolver.setOnClickListener {
            val action1 = registerDirections.actionRegisterToLogin()
            view1.findNavController().navigate(action1)
        }
    }


}