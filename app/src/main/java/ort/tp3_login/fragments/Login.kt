package ort.tp3_login.fragments

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import okhttp3.internal.wait
import ort.tp3_login.R
import ort.tp3_login.activities.activity_turista
import ort.tp3_login.dataclasses.Login
import ort.tp3_login.dataclasses.ServicioService
import ort.tp3_login.dataclasses.UsuarioLogin
import ort.tp3_login.services.RetrofitInstance
import ort.tp3_login.viewModels.ViewModelHomeTurista
import retrofit2.Response


class login : Fragment() {

    lateinit var view1: View
    lateinit var buttonLogin: Button
    lateinit var textRegister: TextView
    lateinit var radioButton: RadioButton
    lateinit var usuario: EditText
    lateinit var password: EditText
    var viewModel: ViewModelHomeTurista? = ViewModelHomeTurista()


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
        password = view1.findViewById(R.id.editTextTextPassword)


        return view1
    }

    override fun onStart() {
        super.onStart()

        buttonLogin.setOnClickListener {

            /* ASI ESTABA ANTES ---->
            var user = usuario.text.toString()
            var pass = password.text.toString()
            val usuarioFetch = fetchLogin(user, pass)

             */
            var user = usuario.text.toString()
            var pass = password.text.toString()

            fun fetcher() = runBlocking(CoroutineName("fetcher")) {
                fetchLogin(user, pass)
            }
            fetcher()
            if (!(TextUtils.isEmpty(user) && TextUtils.isEmpty(pass)) && viewModel?.user != null) {
                //depende el rol del usuario devuelto redirigir a HomeTurista o HomeGuia
                navigatorConArgs(viewModel?.user!!)
            } else {
                Snackbar.make(view1, "Usuario o contraseña incorrectos.", Snackbar.LENGTH_SHORT)
                    .show()
            }


        }

        textRegister.setOnClickListener {
            val action3 = loginDirections.actionLoginToRegister()
            view1.findNavController().navigate(action3)
        }
    }

    private fun navigatorConArgs(args: UsuarioLogin) {
        when (args.role) {
            // 0 -> ir a pantalla admin
            "TOURIST" -> {
                val intent = Intent(context, activity_turista::class.java)
                startActivity(intent)
            }
            "GUIDE" -> {
                val action1 = R.id.action_login_to_containerFragmentGuia
                view1.findNavController().navigate(action1)
            }
            else -> {
                Snackbar.make(view1, "Usuario o contraseña incorrectos.", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private suspend fun fetchLogin(user: String, pass: String) {
        val retService: ServicioService = RetrofitInstance
            .getRetrofitInstance()
            .create(ServicioService::class.java)
        val login = Login(user, pass)
        val response = retService.getLogin(login)
        viewModel?.user = response.body()
    }


    /*
    Asi estaba antes --->
        private fun fetchLogin(user: String, pass: String): UsuarioLogin? {
        val retService: ServicioService = RetrofitInstance
            .getRetrofitInstance()
            .create(ServicioService::class.java)
        val responseLiveData: LiveData<Response<UsuarioLogin>> = liveData {
            val response = retService.getLogin(user, pass)
            Log.d("response", response.toString())
            emit(response)
        }
        var resultado: UsuarioLogin? = null
        responseLiveData.observe(this, Observer {
            val usuario = it.body()
            if (usuario != null) {
                Log.d("usuario", usuario.toString())
                resultado = usuario
            } else {
                Log.d("usuario", "es null")
            }

        })

        return resultado
    }


     */
}
