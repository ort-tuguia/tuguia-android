package ort.tp3_login.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_agregar_servicio.*
import ort.tp3_login.R
import ort.tp3_login.dataclasses.Categorias
import ort.tp3_login.dataclasses.ServicioService
import ort.tp3_login.services.RetrofitInstance
import ort.tp3_login.viewModels.ViewModelGuia
import retrofit2.Response

class FotoAgregarServicio : Fragment() {


    lateinit var view1: View
    lateinit var buttonSiguiente : Button
    lateinit var imageView: ImageView
    lateinit var buttonAgregar: Button
    private val viewModel: ViewModelGuia by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view1 = inflater.inflate(R.layout.fragment_foto_agregar_servicio, container, false)
        buttonSiguiente = view1.findViewById(R.id.buttonSiguiente)
        buttonAgregar = view1.findViewById(R.id.buttonAgregarImagen)
        imageView = view1.findViewById(R.id.imageViewAgregar)
         return view1

    }

    override fun onStart() {
        super.onStart()
        buttonSiguiente.setOnClickListener {
            view1.findNavController().navigate(R.id.action_fotoAgregarServicio_to_agregarServicio)
        }
        buttonAgregar.setOnClickListener {
            pickImageGallery()
        }

    }


    private fun pickImageGallery () {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        getResult.launch(intent)
    }
    private val getResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { it ->
        // var value: String
        var imageUri: Uri? = null
        if (it.resultCode == Activity.RESULT_OK) {
            //value = it.data?.getStringExtra("input")!!
            imageUri = it.data?.data
            viewModel.servicioUrlFoto = imageUri
            imageView.setImageURI(imageUri)
        }
    }



}