package ort.tp3_login.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_registro_turista.*
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.runBlocking
import ort.tp3_login.R
import ort.tp3_login.dataclasses.Login
import ort.tp3_login.dataclasses.ServicioService
import ort.tp3_login.dataclasses.UsuarioLogin
import ort.tp3_login.services.RetrofitInstance

class RegistroTurista : Fragment() {

    lateinit var view1 : View
    lateinit var textViewVolver : TextView
    lateinit var firstName: EditText
    lateinit var username: EditText
    lateinit var lastName: EditText
    lateinit var email: EditText
    lateinit var password: EditText
    var isGuide: Boolean = false

    lateinit var buttonRegistrar : Button
    lateinit var usuarioLogin: UsuarioLogin


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view1 = inflater.inflate(R.layout.fragment_registro_turista, container, false)
        textViewVolver = view1.findViewById(R.id.textViewVolverRegistro)
        firstName = view1.findViewById(R.id.editTextPersonName)
        lastName = view1.findViewById(R.id.editTextTextPersonName2)
        email = view1.findViewById(R.id.editTextTextPersonName3)
        username = view1.findViewById(R.id.editTextTextPersonName4)
        password = view1.findViewById(R.id.editTextTextPassword)
        buttonRegistrar = view1.findViewById(R.id.buttonLogin3)
        return view1
    }

    override fun onStart() {
        super.onStart()
        buttonRegistrar.setOnClickListener {
            fetcher()
            //     val action = registerDirections.actionRegisterToLogin()
            //     view?.findNavController()?.navigate(action)
            //
        }




            textViewVolver.setOnClickListener {
                  val action = RegistroTuristaDirections.actionRegistroTuristaToRegistroEligir()
                 view1.findNavController().navigate(action)
            }
              }
    private suspend fun register(usuarioLogin: UsuarioLogin) : Boolean {
        val retService: ServicioService = RetrofitInstance
            .getRetrofitInstance()
            .create(ServicioService::class.java)
        val response = retService.postRegister(usuarioLogin)

        if(response.isSuccessful){
            return true
        }
        return false
    }
    fun fetcher() = runBlocking(CoroutineName("fetcher")) {
        register(usuarioLogin)
    }


}