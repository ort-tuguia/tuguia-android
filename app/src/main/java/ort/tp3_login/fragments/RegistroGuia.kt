package ort.tp3_login.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import ort.tp3_login.R
import ort.tp3_login.dataclasses.ServicioService
import ort.tp3_login.dataclasses.UsuarioRegister
import ort.tp3_login.services.RetrofitInstance


class RegistroGuia : Fragment() {

    lateinit var view1 : View
    lateinit var buttonVolver : TextView
    lateinit var firstName: EditText
    lateinit var username: EditText
    lateinit var lastName: EditText
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var matricula: EditText
    lateinit var buttonRegistrar : Button
    lateinit var usuarioRegister: UsuarioRegister

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view1 = inflater.inflate(R.layout.fragment_registro_guia, container, false)
        buttonVolver = view1.findViewById(R.id.textViewVolverRegistro)
        firstName = view1.findViewById(R.id.editTextPersonName)
        lastName = view1.findViewById(R.id.editTextTextPersonName2)
        email = view1.findViewById(R.id.editTextTextPersonName3)
        username = view1.findViewById(R.id.editTextTextPersonName4)
        password = view1.findViewById(R.id.editTextTextPassword)
        buttonRegistrar = view1.findViewById(R.id.buttonRegistrar)
        matricula = view1.findViewById(R.id.editTextNumeroMatricula)

        return view1
    }

    override fun onStart() {
        super.onStart()
        buttonRegistrar.setOnClickListener {
            usuarioRegister = UsuarioRegister(
                username.text.toString(),
                firstName.text.toString(),
                lastName.text.toString(),
                email.text.toString(),
                password.text.toString(),
                true
            //TODO Agregar matricula cuando este lista en backend
            )
            var statusCode: Boolean = fetcher()

            if (statusCode) {

                Snackbar.make(view1, "Se creo el usuario ", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(Color.parseColor("#42D727"))
                    .show()
                val action = RegistroGuiaDirections.actionRegistroGuiaToLogin()
                view1.findNavController().navigate(action)
            }
        }



        buttonVolver.setOnClickListener {
            val action = RegistroGuiaDirections.actionRegistroGuiaToRegistroEligir()
            view1.findNavController().navigate(action)
        }
    }
        private suspend fun register() : Boolean {
            val retService: ServicioService = RetrofitInstance
                .getRetrofitInstance()
                .create(ServicioService::class.java)
            val response = retService.postRegister(usuarioRegister)
            if(response.isSuccessful){
                return true
            }
            val jObjError = JSONObject(response.errorBody()!!.string())
            Log.d("Error", jObjError.getString("errors"))
            Snackbar.make(view1, jObjError.getString("message"), Snackbar.LENGTH_SHORT)
                .setBackgroundTint(Color.parseColor("#D72F27"))
                .show()
            return false
        }
        fun fetcher() = runBlocking(CoroutineName("fetcher")) {
            register()
        }
    }


