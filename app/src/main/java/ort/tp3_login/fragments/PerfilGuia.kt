package ort.tp3_login.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import de.hdodenhof.circleimageview.CircleImageView
import ort.tp3_login.R
import ort.tp3_login.viewModels.ViewModelGuia
import ort.tp3_login.viewModels.ViewModelHomeTurista


class PerfilGuia : Fragment() {

    companion object{
        val IMAGE_REQUEST_CODE = 100
    }
    lateinit var circleImageView: CircleImageView
    lateinit var v: View
    private  val viewModel: ViewModelGuia by activityViewModels()

    lateinit var botonEdit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_perfil_guia, container, false)
        v.findViewById<TextView>(R.id.user_name).text = viewModel.user.value?.firstName + " " + viewModel.user.value?.lastName
        v.findViewById<TextView>(R.id.user_email).text = viewModel.user.value?.email
        circleImageView = v.findViewById(R.id.circleImageViewGuia)
        botonEdit = v.findViewById(R.id.button)
        return  v
    }
    override fun onStart() {
        super.onStart()
        botonEdit.setOnClickListener {
            v.findNavController().navigate(R.id.action_perfil_guia_to_guiaEdit)
        }

        circleImageView.setOnClickListener{
            pickImageGallery()
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


}