package ort.tp3_login.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_register.*
import ort.tp3_login.R

class register : Fragment() {

    lateinit var view1 : View
    lateinit var textViewVolver : TextView
    lateinit var firstName: EditText
    lateinit var username: EditText
    lateinit var lastName: EditText
    lateinit var email: EditText
    lateinit var password: EditText
    var isGuide: Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view1 = inflater.inflate(R.layout.fragment_register, container, false)
        textViewVolver = view1.findViewById(R.id.textViewVolverRegistro)
        firstName = view1.findViewById(R.id.editTextPersonName)
        lastName = view1.findViewById(R.id.editTextTextPersonName2)
        email = view1.findViewById(R.id.editTextTextPersonName3)
        username = view1.findViewById(R.id.editTextTextPersonName4)
        password = view1.findViewById(R.id.editTextTextPassword)
        return view1
    }

    override fun onStart() {
        super.onStart()
        buttonLogin3.setOnClickListener {



            val action = registerDirections.actionRegisterToLogin()
            view?.findNavController()?.navigate(action)
        }
        textViewVolver.setOnClickListener {
            val action1 = registerDirections.actionRegisterToLogin()
            view1.findNavController().navigate(action1)
        }
    }


}