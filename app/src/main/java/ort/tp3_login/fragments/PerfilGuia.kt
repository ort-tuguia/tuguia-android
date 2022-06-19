package ort.tp3_login.fragments

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import ort.tp3_login.R
import ort.tp3_login.dataclasses.Photo
import ort.tp3_login.dataclasses.ServicioService
import ort.tp3_login.services.RetrofitInstance
import ort.tp3_login.viewModels.ViewModelGuia
import ort.tp3_login.viewModels.ViewModelHomeTurista
import java.util.*


class PerfilGuia : Fragment() {

    companion object{
        val IMAGE_REQUEST_CODE = 100
    }
    lateinit var circleImageView: CircleImageView
    lateinit var v: View
    private  val viewModel: ViewModelGuia by activityViewModels()

    lateinit var botonEdit: Button
    lateinit var storageReference: StorageReference
    lateinit var servicioService: ServicioService

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

        loadImageInCircle()
        if(circleImageView.drawable == null){
            circleImageView.setImageResource(R.drawable.icon_profile)
        }
        botonEdit = v.findViewById(R.id.botonEdit)
        servicioService = RetrofitInstance
            .getRetrofitInstance()
            .create(ServicioService::class.java)
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
    private fun loadImageInCircle() {

        //TODO cuando se arregle backend cargar value.foto.id
        val imageName : String? = viewModel.user.value?.photoUrl
        if (imageName != null) {
            Log.d("imagen", imageName)

        }
//       ACAA FUNCION
        //
        //        Log.d("imagen", viewModel.user.value.toString())
//     storageReference = FirebaseStorage.getInstance().reference.child("images/ProfilePictures/$imageName")
//
//        val localFile = File.createTempFile("tempImage", "jpg")
//        storageReference.getFile(localFile).addOnSuccessListener{
//            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
//            circleImageView.setImageBitmap(bitmap)
//        }.addOnFailureListener{
//            Toast.makeText(context, "Error al traer la imagen", Toast.LENGTH_SHORT).show()
//        }

        //HASTA ACAA

//        //ACA FUNCIONA SI LO QUE VIENE ES UNA URL

        Picasso.get().load(viewModel.user.value?.photoUrl?.toUri()).into(circleImageView);
        //circleImageView.setImage(viewModel.user.value?.photoUrl?.toUri())


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

        val progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Subiendo imagen...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val fileName = UUID.randomUUID().toString()
        var urlPhoto:String = ""
        storageReference = FirebaseStorage.getInstance().reference.child("images/ProfilePictures/$fileName")
        if (imageUri != null) {
            storageReference.putFile(imageUri!!).addOnSuccessListener {
                circleImageView.setImageURI(imageUri)
                Toast.makeText(context, "Imagen subida correctamente", Toast.LENGTH_LONG).show()
                it.storage.downloadUrl.addOnSuccessListener {task->
                    urlPhoto = task.normalizeScheme().toString()
                    var photo: Photo = Photo(urlPhoto)
                    upload(photo)
                    Log.d("URL", task.toString())
                }
                if (progressDialog.isShowing) progressDialog.dismiss()
            }.addOnFailureListener {
                if (progressDialog.isShowing) progressDialog.dismiss()
                Toast.makeText(context, "Error al subir imagen", Toast.LENGTH_LONG).show()

            }
        }
        }
    }



    fun upload(photo: Photo) = runBlocking(CoroutineName("upload"))
    {
        Log.d("Response -- > Antes de ir a backend", photo.toString())
        Log.d("Response -- > Antes de ir a backend", viewModel.token)
        //TODO Cargue URL correctamente a backend
        var response = servicioService.putPhoto(photo, viewModel.token)

        if(response.isSuccessful){
            Log.d("Response", response.body().toString())
            Log.d("Response", response.code().toString())
            viewModel.user.value=response.body()
        }else{
            Log.d("Response", response.body().toString())
            Log.d("Response", response.code().toString())
            val jObjError = JSONObject(response.errorBody()!!.string())
            Log.d("Response -- > Error", jObjError.getString("message"))
        }

    }


}