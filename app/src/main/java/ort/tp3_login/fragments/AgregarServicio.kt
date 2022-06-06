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
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_agregar_servicio.*
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import ort.tp3_login.R
import ort.tp3_login.dataclasses.CrearServicio
import ort.tp3_login.dataclasses.ServicioService
import ort.tp3_login.services.RetrofitInstance
import ort.tp3_login.viewModels.ViewModelHomeTurista


class AgregarServicio : Fragment() {

    lateinit var view1 : View
    lateinit var buttonAgregar : Button
    lateinit var volver: TextView
    lateinit var servicio: CrearServicio

    lateinit var name: EditText
    lateinit var description: EditText
    lateinit var location: EditText
    lateinit var price: EditText
    lateinit var urlFoto: EditText

    private  val viewModel: ViewModelHomeTurista by activityViewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view1 = inflater.inflate(R.layout.fragment_agregar_servicio, container, false)
        buttonAgregar = view1.findViewById(R.id.Agregar)
        volver = view1.findViewById(R.id.Volver)
        name = view1.findViewById(R.id.Nombre)
        description = view1.findViewById(R.id.Descripcion)
        location = view1.findViewById(R.id.Ubicacion)
        price = view1.findViewById(R.id.Precio)
        urlFoto = view1.findViewById(R.id.UrlServicio)


        return view1
    }

    override fun onStart() {
        super.onStart()
        buttonAgregar.setOnClickListener {
            servicio = CrearServicio(
                name.text.toString(),
                description.text.toString(),
                location.text.toString().toDouble(),
                location.text.toString().toDouble(),
                price.text.toString().toDouble(),
                urlFoto.text.toString(),
                "d7030b7b-56d4-4544-b563-477e14e6d8df",
                "turista",

            )
            var statusCode: Boolean = fetcher()

            if (statusCode) {

                Snackbar.make(view1, "Se creo el Servicio ", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(Color.parseColor("#42D727"))
                    .show()
                view1.findNavController().navigate(R.id.action_agregarServicio_to_home_guia)
            }
        }



        volver.setOnClickListener {
            view1.findNavController().navigate(R.id.action_agregarServicio_to_home_guia)
        }
    }
    private suspend fun register() : Boolean {
        val retService: ServicioService = RetrofitInstance
            .getRetrofitInstance()
            .create(ServicioService::class.java)
        val response = retService.postCrearServicio(servicio, viewModel.token)
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


