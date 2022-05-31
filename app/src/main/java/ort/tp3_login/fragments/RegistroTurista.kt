package ort.tp3_login.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import ort.tp3_login.R
import ort.tp3_login.dataclasses.ServicioService
import ort.tp3_login.dataclasses.UsuarioRegister
import ort.tp3_login.services.RetrofitInstance


class RegistroTurista : Fragment() {

    lateinit var view1: View
    lateinit var textViewVolver: TextView
    lateinit var firstName: EditText
    lateinit var username: EditText
    lateinit var lastName: EditText
    lateinit var email: EditText
    lateinit var password: EditText

    lateinit var buttonRegistrar: Button
    lateinit var usuarioRegister: UsuarioRegister


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
            usuarioRegister = UsuarioRegister(
                username.text.toString(),
                firstName.text.toString(),
                lastName.text.toString(),
                email.text.toString(),
                password.text.toString(),
                false
            )
            var statusCode: Boolean = fetcher()

            if (statusCode) {

                Snackbar.make(view1, "Se creo el usuario ", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(Color.parseColor("#42D727"))
                    .show()
                val action = RegistroTuristaDirections.actionRegistroTuristaToLogin()
                view1.findNavController().navigate(action)
            }
        }
        textViewVolver.setOnClickListener {
            val action = RegistroTuristaDirections.actionRegistroTuristaToRegistroEligir()
            view1.findNavController().navigate(action)
        }
    }

    private suspend fun register(): Boolean {
        val retService: ServicioService = RetrofitInstance
            .getRetrofitInstance()
            .create(ServicioService::class.java)
        val response = retService.postRegister(usuarioRegister)
        if (response.isSuccessful) {
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