package ort.tp3_login.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.runBlocking
import ort.tp3_login.R
import ort.tp3_login.dataclasses.ChangePasswordDataClass
import ort.tp3_login.dataclasses.ServicioService
import ort.tp3_login.services.RetrofitInstance
import ort.tp3_login.viewModels.ViewModelGuia


class ChangePasswordGuia: Fragment() {
    private val viewModel: ViewModelGuia by activityViewModels()
    lateinit var v: View
    lateinit var editTextCurrentPassword : EditText
    lateinit var editTextNewPassword : EditText
    lateinit var editTextConfirmNewPassword : EditText
    lateinit var buttonGuardar : Button
    lateinit var  changePassword: ChangePasswordDataClass
    lateinit var retService: ServicioService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_change_password, container, false)
        editTextCurrentPassword = v.findViewById(R.id.editTextCurrentPassword)
        editTextNewPassword = v.findViewById(R.id.editTextNewPassword)
        editTextConfirmNewPassword = v.findViewById(R.id.editTextConfirmNewPassword)
        buttonGuardar = v.findViewById(R.id.buttonGuardar)
        retService= RetrofitInstance
            .getRetrofitInstance()
            .create(ServicioService::class.java)
        return  v
    }

    override fun onStart(){
        super.onStart()
        buttonGuardar.setOnClickListener {
            if(editTextNewPassword.text.toString() == editTextConfirmNewPassword.text.toString()) {
                changePassword = ChangePasswordDataClass(
                    editTextCurrentPassword.text.toString(),
                    editTextNewPassword.text.toString(),
                    editTextConfirmNewPassword.text.toString()
                )
                fetcher()
            }
        }
    }
    fun fetcher() = runBlocking(CoroutineName("fetcher")) {
        val response = retService.putPassword(changePassword,viewModel.token)
        if(response.isSuccessful) {
            viewModel.user.value = response.body()
            Snackbar.make(v, "Contraseña cambiada con exito", Snackbar.LENGTH_LONG).show()
            v.findNavController().navigate(R.id.action_changePasswordGuia_to_guiaEdit)
        }else{
            Snackbar.make(v, "Contraseña actual incorrecta", Snackbar.LENGTH_LONG).show()
        }
    }

}