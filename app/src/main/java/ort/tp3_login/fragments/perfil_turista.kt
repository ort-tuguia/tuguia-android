package ort.tp3_login.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_home_turista.*
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.runBlocking
import ort.tp3_login.R
import ort.tp3_login.dataclasses.CategoriaItem
import ort.tp3_login.dataclasses.ServicioService
import ort.tp3_login.services.RetrofitInstance
import ort.tp3_login.services.RetrofitInstance.Companion.getRetrofitInstance
import ort.tp3_login.viewModels.ViewModelHomeTurista




class perfil_turista : Fragment() {
    companion object{
        val IMAGE_REQUEST_CODE = 100


    }
    lateinit var circleImageView: CircleImageView
    lateinit var v: View
    private  val viewModel: ViewModelHomeTurista by activityViewModels()

    lateinit var botonCategorias: Button
    lateinit var botonEdit: Button

    var selectedCategorie: ArrayList<Boolean> = ArrayList<Boolean>()
    var categorieListInt : ArrayList<Int> = ArrayList<Int>()
    lateinit var resultCategorie: MutableListIterator<CategoriaItem>
    var categoriesNombre : ArrayList<String> = ArrayList<String>()
    lateinit var dialog : AlertDialog
    lateinit var maxKm : TextView
    var categoriesParaBackend : ArrayList<String> = ArrayList<String>()
    var categoriesAux : MutableMap<String, CategoriaItem> = HashMap()
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
        v=inflater.inflate(R.layout.fragment_perfil_turista, container, false)
        v.findViewById<TextView>(R.id.user_name).text = viewModel.user.value?.firstName + " " + viewModel.user.value?.lastName
        v.findViewById<TextView>(R.id.user_email).text = viewModel.user.value?.email
        botonEdit = v.findViewById(R.id.botonEdit)
        botonCategorias = v.findViewById(R.id.buttonCategorias)
        circleImageView = v.findViewById(R.id.circleImageViewTurista)
        resultCategorie = viewModel.categorias
        botonCategorias.setOnClickListener{
            createDialog()
        }
        servicioService = RetrofitInstance
        .getRetrofitInstance()
            .create(ServicioService::class.java)
        return  v
    }

    override fun onStart() {
        super.onStart()

        circleImageView.setOnClickListener{
            pickImageGallery()
        }

        botonEdit.setOnClickListener{
            v.findNavController().navigate(R.id.action_perfil_turista_to_turistaEdit)
        }


    }

    private fun createDialog(){

        //TODO Ver porque no carga al cambio de pantalla
        //TODO Guardar favoritos seleccionados en backend
        val builder = AlertDialog.Builder(context)
        if(categoriesNombre.isEmpty()) {
            resultCategorie.forEach { categoria ->
                categoriesNombre.add(categoria.name)
                categoriesAux[categoria.name] = categoria
            }
        }
        if (viewModel.selectedCategorie.isEmpty()){
            repeat(categoriesNombre.count()) {
                viewModel.selectedCategorie.add(false)
            }
        }
        val categoriasArray: Array<String> = categoriesNombre.toTypedArray()


        builder.setTitle("Seleccione categorias")
        builder.setMultiChoiceItems(
            categoriasArray,
            viewModel.selectedCategorie.toBooleanArray()
        ) { dialog, which, isChecked ->}
        builder.setPositiveButton("Enviar"){dialog,which->
            val alertDialog = dialog as AlertDialog
            val sparseBooleanArray = alertDialog.listView.checkedItemPositions
            var counter = 0

            /*textViewSelect.text = ""*/
            categoriasArray.forEachIndexed { index, s ->
                if (sparseBooleanArray.get(index, false)) {
                    categoriesAux[s]?.let { categoriesParaBackend.add(it.id) }
                    Log.d("Categoria --> IF ID" , categoriesParaBackend[counter].toString())
                    counter += 1
                }
            }
            if(!categoriesParaBackend.isEmpty()){
                fetcher()
                categoriesParaBackend.clear()
            }

            if (counter > 0) {


            }
        }
        builder.setNeutralButton("Cancelar"){dialog,which->

        }
        builder.setCancelable(false)

        val dialog = builder.create()
        dialog.show()

        if((selectedCategorie.filter { it }).size < 2){
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false
        }
        dialog.listView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val sparseBooleanArray = dialog.listView.checkedItemPositions
                var checkedItems = 0
                categoriasArray.forEachIndexed { index, s ->
                    if (sparseBooleanArray.get(index,false)){
                        checkedItems +=1
                        viewModel.selectedCategorie[index] = true
                    }else{
                        viewModel.selectedCategorie[index] = false
                    }
                }

                dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                    .isEnabled = checkedItems >=1
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


    fun fetcher() = runBlocking(CoroutineName("fetcher")) {
        var response = servicioService.putCategories(categoriesParaBackend, viewModel.token)
        Log.d("response --> Categorias Backend",response.body().toString())
    }

}