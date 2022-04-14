package ort.tp3_login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import androidx.navigation.findNavController


class login : Fragment() {
    lateinit var view1 : View
    lateinit var buttonLogin : Button
    lateinit var textRegister : TextView
    lateinit var radioButton : RadioButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        view1 = inflater.inflate(R.layout.fragment_login, container, false)

        buttonLogin = view1.findViewById(R.id.buttonLogin)
        textRegister = view1.findViewById(R.id.textviewRegistrar)
        radioButton = view1.findViewById(R.id.radioButton)
        return view1
    }

    override fun onStart() {
        super.onStart()

        buttonLogin.setOnClickListener {
            if(radioButton.isChecked){
                val action1 = loginDirections.actionLoginToHomeGuia()
                view1.findNavController().navigate(action1)
            }else{
                val action2 = loginDirections.actionLoginToHomeTurista()
                view1.findNavController().navigate(action2)
            }

        }

        textRegister.setOnClickListener {
            val action3 = loginDirections.actionLoginToRegister()
            view1.findNavController().navigate(action3)
        }
    }



}