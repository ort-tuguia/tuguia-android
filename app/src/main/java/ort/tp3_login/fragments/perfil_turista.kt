package ort.tp3_login.fragments

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.AdapterView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.internal.StorageReferenceUri
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_turista.*
import kotlinx.android.synthetic.main.activity_turista.view.*
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import ort.tp3_login.R
import ort.tp3_login.dataclasses.CategoriaItem
import ort.tp3_login.dataclasses.Photo
import ort.tp3_login.dataclasses.ServicioService
import ort.tp3_login.services.RetrofitInstance
import ort.tp3_login.viewModels.ViewModelHomeTurista
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class perfil_turista : Fragment() {
    companion object {
        val IMAGE_REQUEST_CODE = 100
    }

    lateinit var circleImageView: CircleImageView
    lateinit var v: View
    private val viewModel: ViewModelHomeTurista by activityViewModels()

    lateinit var botonCategorias: Button
    lateinit var botonEdit: Button

    var selectedCategorie: ArrayList<Boolean> = ArrayList<Boolean>()

    lateinit var resultCategorie: MutableListIterator<CategoriaItem>
    var categoriesNombre: ArrayList<String> = ArrayList<String>()

    var categoriesParaBackend: ArrayList<String> = ArrayList<String>()
    var categoriesAux: MutableMap<String, CategoriaItem> = HashMap()
    lateinit var servicioService: ServicioService
    lateinit var storageReference: StorageReference
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
        v = inflater.inflate(R.layout.fragment_perfil_turista, container, false)
        v.findViewById<TextView>(R.id.user_name).text =
            viewModel.user.value?.firstName + " " + viewModel.user.value?.lastName
        v.findViewById<TextView>(R.id.user_email).text = viewModel.user.value?.email
        botonEdit = v.findViewById(R.id.botonEdit)
        botonCategorias = v.findViewById(R.id.buttonCategorias)


        // Set imagen del backend desde firebase


        circleImageView = v.findViewById(R.id.circleImageViewTurista)
        //TODO Cuando se arregle Backend
         loadImageInCircle()
        resultCategorie = viewModel.categorias
        botonCategorias.setOnClickListener {
            createDialog()
        }
        servicioService = RetrofitInstance
            .getRetrofitInstance()
            .create(ServicioService::class.java)

        if(circleImageView.drawable == null){
            circleImageView.setImageResource(R.drawable.icon_profile)
        }
        return v
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

    override fun onStart() {
        super.onStart()

        circleImageView.setOnClickListener {
            pickImageGallery()
        }

        botonEdit.setOnClickListener {
            v.findNavController().navigate(R.id.action_perfil_turista_to_turistaEdit)
        }


    }

    private fun createDialog() {
        val builder = AlertDialog.Builder(context)
        if (categoriesNombre.size == 0) {
            resultCategorie.forEach { categoria ->
                categoriesNombre.add(categoria.name)
                categoriesAux[categoria.name] = categoria
            }
        }
        //if (viewModel.selectedCategorie.isEmpty()){
        repeat(categoriesNombre.count()) { i ->
            var flag = false
            if (!viewModel.user.value?.favCategories?.isEmpty()!!) {
                viewModel.user.value?.favCategories?.forEach { favCategorie ->
                    if (categoriesNombre[i] == favCategorie.name) {
                        flag = true
                    }
                }
                if (viewModel.selectedCategorie.size == categoriesNombre.size) {
                    viewModel.selectedCategorie[i] = flag
                } else {
                    viewModel.selectedCategorie.add(flag)
                }

            } else {
                if (viewModel.selectedCategorie.size == categoriesNombre.size) {
                    viewModel.selectedCategorie[i] = false
                } else {
                    viewModel.selectedCategorie.add(false)
                }

            }


        }
        //}
        val categoriasArray: Array<String> = categoriesNombre.toTypedArray()


        builder.setTitle("Seleccione categorias")
        builder.setMultiChoiceItems(
            categoriasArray,
            viewModel.selectedCategorie.toBooleanArray()
        ) { dialog, which, isChecked -> }
        builder.setPositiveButton("Enviar") { dialog, which ->
            val alertDialog = dialog as AlertDialog
            val sparseBooleanArray = alertDialog.listView.checkedItemPositions
            var counter = 0

            /*textViewSelect.text = ""*/
            categoriasArray.forEachIndexed { index, s ->
                if (sparseBooleanArray.get(index, false)) {
                    categoriesAux[s]?.let { categoriesParaBackend.add(it.id) }
                    Log.d("Categoria --> IF ID", categoriesParaBackend[counter].toString())
                    counter += 1
                }
            }
            if (!categoriesParaBackend.isEmpty()) {
                fetcher()

                categoriesParaBackend.clear()

            }

            if (counter > 0) {


            }
        }
        builder.setNeutralButton("Cancelar") { dialog, which ->

        }
        builder.setCancelable(false)

        val dialog = builder.create()
        dialog.show()

        if ((selectedCategorie.filter { it }).size < 2) {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false
        }
        dialog.listView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val sparseBooleanArray = dialog.listView.checkedItemPositions
                var checkedItems = 0
                categoriasArray.forEachIndexed { index, s ->
                    if (sparseBooleanArray.get(index, false)) {
                        checkedItems += 1
                        viewModel.selectedCategorie[index] = true
                    } else {
                        viewModel.selectedCategorie[index] = false
                    }
                }

                dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                    .isEnabled = checkedItems >= 1
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



    fun upload(photo:Photo) = runBlocking(CoroutineName("upload"))
    {

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

    fun fetcher() = runBlocking(CoroutineName("fetcher")) {
        var response = servicioService.putCategories(categoriesParaBackend, viewModel.token)
        if (response.isSuccessful) {
            viewModel.user.value!!.favCategories = response.body()!!.favCategories
            Log.d("response -- > PerfilTurista", response.body().toString())
        }
    }


}