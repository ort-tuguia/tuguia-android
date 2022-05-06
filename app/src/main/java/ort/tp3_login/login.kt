package ort.tp3_login

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.activity_main.*


class login : Fragment() {
    lateinit var view1 : View
    lateinit var buttonLogin : Button
    lateinit var textRegister : TextView
    lateinit var radioButton : RadioButton
    lateinit var usuario : EditText


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        view1 = inflater.inflate(R.layout.fragment_login, container, false)

        buttonLogin = view1.findViewById(R.id.buttonLogin)
        textRegister = view1.findViewById(R.id.textviewRegistrar)
        radioButton = view1.findViewById(R.id.radioButton)
        usuario = view1.findViewById(R.id.editTextPersonName)

        return view1
    }

    override fun onStart() {
        super.onStart()


        buttonLogin.setOnClickListener {
            var args = usuario.text.toString()
            if(!TextUtils.isEmpty(args)) {
                navigatorConArgs(args)
            }else{
                Toast.makeText(context, "Ingrese un nombre",Toast.LENGTH_SHORT).show()
            }
        }

        textRegister.setOnClickListener {
            val action3 = loginDirections.actionLoginToRegister()
            view1.findNavController().navigate(action3)
        }
    }

    private fun navigatorConArgs(args : String) {
        val bundle: Bundle = bundleOf("usuario" to args)
        if(radioButton.isChecked){
            val action1 = R.id.action_login_to_containerFragmentGuia
            view1.findNavController().navigate(action1,bundle)
        }else{
           val intent = Intent(context, activity_turista::class.java)
            startActivity(intent)
        }
    }


}