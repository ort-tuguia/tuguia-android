package ort.tp3_login.fragments

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
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
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.liveData
import androidx.navigation.findNavController
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.ktx.widget.PlaceSelectionError
import com.google.android.libraries.places.ktx.widget.PlaceSelectionSuccess
import com.google.android.libraries.places.ktx.widget.placeSelectionEvents
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.fragment_agregar_servicio.*
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import ort.tp3_login.R
import ort.tp3_login.dataclasses.*
import ort.tp3_login.services.RetrofitInstance
import ort.tp3_login.viewModels.ViewModelGuia
import ort.tp3_login.viewModels.ViewModelHomeTurista
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class AgregarServicio : Fragment() {

    lateinit var view1 : View
    lateinit var buttonAgregar : Button
    lateinit var volver: TextView
    lateinit var servicio: CrearServicio

    lateinit var name: EditText
    lateinit var description: EditText
    lateinit var categoria: TextView
    //lateinit var location: TextView
    lateinit var price: EditText
    //lateinit var urlFoto: EditText

    private  val viewModel: ViewModelGuia by activityViewModels()
    lateinit var storageReference: StorageReference

    var resultCategorie: MutableListIterator<CategoriaItem>? = null

    private var arraylistCategorias : ArrayList <CategoriaItem>? = ArrayList()

    //places
    //private lateinit var placesClient: PlacesClient
    //private lateinit var responseView: TextView




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view1 = inflater.inflate(R.layout.fragment_agregar_servicio, container, false)
        buttonAgregar = view1.findViewById(R.id.Agregar)
        volver = view1.findViewById(R.id.Volver)
        name = view1.findViewById(R.id.Nombre)
        description = view1.findViewById(R.id.Descripcion)
        //location = view1.findViewById(R.id.Ubicacion)
        price = view1.findViewById(R.id.Precio)
        //urlFoto = view1.findViewById(R.id.UrlServicio)
        categoria = view1.findViewById(R.id.categoria)
        fetchCategories()

        //google places
        //activarPlaces ()
        if (viewModel.servicioItemSeleccionado != null) {

            name.setText(viewModel.servicioItemSeleccionado!!.name)
            description.setText(viewModel.servicioItemSeleccionado!!.description)
            price.setText(viewModel.servicioItemSeleccionado!!.price.toString())
            buttonAgregar.setText("Editar")
            requireActivity().title = "Editar Servicio"
        }



        return view1
    }

   /* private fun activarPlaces() {
        placesClient = Places.createClient(this.activity)

        responseView = view1.findViewById(R.id.autocomplete_response_content)
        val autocompleteFragment = activity?.supportFragmentManager?.findFragmentById(R.id.autocomplete_fragment)
                as AutocompleteSupportFragment
        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(listOf(Place.Field.NAME, Place.Field.ID, Place.Field.LAT_LNG, Place.Field.ADDRESS))

        // Listen to place selection events
        lifecycleScope.launchWhenCreated {
            autocompleteFragment.placeSelectionEvents()?.collect { event ->
                when (event) {
                    is PlaceSelectionSuccess -> {
                        val place = event.place
                        //responseView.text = StringUtil.stringifyAutocompleteWidget(place, false)
                    }
                    is PlaceSelectionError -> Toast.makeText(
                        context,
                        "Failed to get place '${event.status.statusMessage}'",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }


        }
    }*/

    fun savePictureOnFirebase(){
        val progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Subiendo imagen...")
        progressDialog.setCancelable(false)
        progressDialog.show()
        val fileName = UUID.randomUUID().toString()
        var urlPhoto : String = ""
        storageReference = FirebaseStorage.getInstance().reference.child("images/activities/$fileName")
        if(viewModel.servicioUrlFoto.toString()!=null){
            storageReference.putFile(viewModel.servicioUrlFoto!!).addOnSuccessListener {
                Toast.makeText(context, "Imagen subida correctamente", Toast.LENGTH_LONG).show()
                it.storage.downloadUrl.addOnSuccessListener { task ->
                    urlPhoto = task.normalizeScheme().toString()
                    var photo : Photo = Photo(urlPhoto)
                    viewModel.servicioUrlFoto = urlPhoto.toUri()
                    createServicio()
                }
                if(progressDialog.isShowing){
                    progressDialog.dismiss()
                }
            }.addOnFailureListener{
                if(progressDialog.isShowing){
                    progressDialog.dismiss()
                }
                Toast.makeText(context, "Error al subir imagen", Toast.LENGTH_LONG).show()
            }


        }

    }
    fun createServicio() {
            if (viewModel.servicioCategoriaId == "" && viewModel.servicioItemSeleccionado != null) {
                //viewModel.servicioCategoriaId = viewModel.servicioItemSeleccionado!!.category.id
                //TODO category id
            }

        servicio = CrearServicio(
            name.text.toString(),
            description.text.toString(),
            viewModel.servicioLocationlat.toString().toDouble(),
            viewModel.servicioLocationlon.toString().toDouble(),
            price.text.toString().toDouble(),
            listOf(Photo(viewModel.servicioUrlFoto.toString())),
            viewModel.servicioCategoriaId,
            viewModel.user.value?.username.toString(),

            )
        var statusCode: Boolean = fetcher()

        if (viewModel.servicioItemSeleccionado != null) {
            var statusCode: Boolean = fetcherPutServicio()
            if (statusCode) {

                Snackbar.make(view1, "Se cambió el Servicio ", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(Color.parseColor("#42D727"))
                    .show()
                view1.findNavController().navigate(R.id.action_agregarServicio_to_home_guia)
            }
        } else {

            var statusCode: Boolean = fetcherCrearServicio()

            if (statusCode) {

                Snackbar.make(view1, "Se creo el Servicio ", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(Color.parseColor("#42D727"))
                    .show()
                view1.findNavController().navigate(R.id.action_agregarServicio_to_home_guia)
            }
        }
    }
    override fun onStart() {
        super.onStart()

        buttonAgregar.setOnClickListener {
            savePictureOnFirebase()


        }



        volver.setOnClickListener {
            //view1.findNavController().navigate(R.id.action_agregarServicio_to_home_guia)
        }
       // location.setOnClickListener{
            //view1.findNavController().navigate(R.id.action_agregarServicio_to_mapsAgregarServicio)
        //}
        categoria.setOnClickListener{
            openDialog()
        }
    }

    private fun openDialog() {

            MaterialAlertDialogBuilder(view1.context)
                .setTitle("Categoria")
                .setItems(viewModel.categorias)
                    { dialog, which ->
                        arraylistCategorias?.forEach {
                            viewModel.servicioCategoriaId = arraylistCategorias?.get(which)?.id.toString()
                        }
                    }.show()
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

    private fun subirFoto() {

    }

    private fun fetchCategories(){
        val retService : ServicioService = RetrofitInstance
            .getRetrofitInstance()
            .create(ServicioService::class.java)
        val responseLiveData : LiveData<Response<Categorias>> = liveData{
            val response = retService.getCategories(viewModel.token)
            Log.d("Categorias", response.body().toString())
            emit(response)
        }
        responseLiveData.observe(viewLifecycleOwner, Observer{
            val categoriasList = it.body()?.listIterator()
            if (categoriasList != null) {
                //Log.d("categoriasList", categoriasList.toString())
                 var i : Int = 0
                resultCategorie = categoriasList

                resultCategorie?.forEach { categoria ->
                    arraylistCategorias?.add(categoria)
                }
                viewModel.categorias = arraylistCategorias?.let { Array(it.size) { i -> arraylistCategorias!!.get(i).name } }
            }else{
                Log.d("categoriasList","es null")
            }
        })

    }
    fun fetcherCrearServicio() = runBlocking(CoroutineName("fetcherCrearServicio")) {
        crearServicio()
    }

    fun fetcherPutServicio() = runBlocking(CoroutineName("fetcherPutServicio")) {
        putServicio()
    }
    private suspend fun crearServicio(): Boolean {
        val retService: ServicioService = RetrofitInstance
            .getRetrofitInstance()
            .create(ServicioService::class.java)
        val response = retService.postCrearServicio(servicio, viewModel.token)
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
    private suspend fun putServicio(): Boolean {

        val retService: ServicioService = RetrofitInstance
            .getRetrofitInstance()
            .create(ServicioService::class.java)
        val response = retService.putActividad(
            viewModel.servicioItemSeleccionado!!.id,
            servicio,
            viewModel.token
        )
        Log.d("response", response.toString())
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
}


