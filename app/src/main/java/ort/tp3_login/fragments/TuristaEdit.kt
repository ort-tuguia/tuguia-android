package ort.tp3_login.fragments

import android.app.Activity
import android.content.Intent
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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import ort.tp3_login.R
import ort.tp3_login.dataclasses.Phone
import ort.tp3_login.dataclasses.ServicioService
import ort.tp3_login.dataclasses.UsuarioEdit
import ort.tp3_login.services.RetrofitInstance
import ort.tp3_login.viewModels.ViewModelGuia
import ort.tp3_login.viewModels.ViewModelHomeTurista


class TuristaEdit : Fragment() {

    lateinit var usuario: UsuarioEdit

    lateinit var circleImageView: CircleImageView

    private val viewModel: ViewModelHomeTurista by activityViewModels()

    lateinit var v: View

    lateinit var nombre: EditText
    lateinit var apellido: EditText
    lateinit var email: EditText
    lateinit var botonGuardar: Button
    lateinit var botonTelefono: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        v = inflater.inflate(R.layout.fragment_turista_edit, container, false)
        nombre = v.findViewById(R.id.name)
        apellido = v.findViewById(R.id.lastName)
        email = v.findViewById(R.id.mail)
        botonTelefono = v.findViewById(R.id.buttonTelefono)
        botonGuardar = v.findViewById(R.id.buttonGuardar)
        circleImageView = v.findViewById(R.id.circleImageViewGuia)

        nombre.setText(viewModel.user.value?.firstName.toString())
        apellido.setText(viewModel.user.value?.lastName.toString())
        email.setText(viewModel.user.value?.email.toString())


        return v
    }

    override fun onStart() {
        super.onStart()
        botonTelefono.setOnClickListener {
            v.findNavController().navigate(R.id.action_turistaEdit_to_telefonosTurista)
        }

        circleImageView.setOnClickListener{
            pickImageGallery()
        }


        botonGuardar.setOnClickListener{
            usuario = UsuarioEdit (
                nombre.text.toString(),
                apellido.text.toString(),
                email.text.toString(),
            )
            val statusCode: Boolean = fetcher()
            if (statusCode) {

                Snackbar.make(v, "Se actualizaron los datos.", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(Color.parseColor("#42D727"))
                    .show()
                //val action = RegistroGuiaDirections.actionRegistroGuiaToLogin()
                val action = TuristaEditDirections.actionTuristaEditToPerfilTurista()
                v.findNavController().navigate(action)
            } else {
                Snackbar.make(v, "No se pudo actualizar los datos.", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(Color.parseColor("#FF0000"))
                    .show()
            }
        }





    }

    private fun pickImageGallery () {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        getResult.launch(intent)
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {
            if(it.resultCode == Activity.RESULT_OK){
                val value = it.data?.getStringExtra("input")
                circleImageView.setImageURI(it.data?.data)
                //circleImageView.setImageURI(value)
            }
        }

    private suspend fun register() : Boolean {
        val retService: ServicioService = RetrofitInstance
            .getRetrofitInstance()
            .create(ServicioService::class.java)
        val response = retService.putUsuario(
            usuario,
            viewModel.token
        )
        if(response.isSuccessful){
            Log.d("Response", response.body().toString())
            viewModel.user.value = response.body()
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