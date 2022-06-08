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
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import ort.tp3_login.R
import ort.tp3_login.dataclasses.Phone
import ort.tp3_login.dataclasses.ServicioService
import ort.tp3_login.services.RetrofitInstance
import ort.tp3_login.viewModels.ViewModelGuia
import ort.tp3_login.viewModels.ViewModelHomeTurista


class Telefonos : Fragment() {

    lateinit var v: View

    private  val viewModel: ViewModelHomeTurista by activityViewModels()

    lateinit var telefono: EditText
    lateinit var descripcion: EditText
    lateinit var buttonGuardar: Button

     var objTelefono: ArrayList<Phone> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.fragment_telefonos2, container, false)
        telefono = v.findViewById(R.id.telefono)
        descripcion = v.findViewById(R.id.descripcion)
        buttonGuardar = v.findViewById(R.id.buttonGuardar)
        return v
    }

    override fun onStart() {
        super.onStart()

        //TODO traer del viewmodel los telefonos del usuario

        buttonGuardar.setOnClickListener{

            if(!viewModel.user.value?.phones?.isEmpty()!!){
                viewModel.user.value?.phones?.forEach{
                    objTelefono.add(it)
                }
            }
            objTelefono.add(Phone (
                telefono.text.toString(),
                descripcion.text.toString()
            ))
            var statusCode: Boolean = fetcher()

            if (statusCode) {

                Snackbar.make(v, "Se actualizo el telefono", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(Color.parseColor("#42D727"))
                    .show()
                v.findNavController().navigate(R.id.action_telefonos_to_guiaEdit)
            }
        }
    }



    private suspend fun register() : Boolean {
        val retService: ServicioService = RetrofitInstance
            .getRetrofitInstance()
            .create(ServicioService::class.java)
        val response = retService.putPhone(
            objTelefono,
            viewModel.token
        )
        if(response.isSuccessful){
            return true
        }
        val jObjError = JSONObject(response.errorBody()!!.string())
        Log.d("Error", jObjError.getString("message"))
        Snackbar.make(v, jObjError.getString("message"), Snackbar.LENGTH_SHORT)
            .setBackgroundTint(Color.parseColor("#D72F27"))
            .show()
        return false
    }
    fun fetcher() = runBlocking(CoroutineName("fetcher")) {
        register()
    }






}