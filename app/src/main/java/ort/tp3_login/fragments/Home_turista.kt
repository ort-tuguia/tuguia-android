package ort.tp3_login.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_home_guia.view.*
import kotlinx.android.synthetic.main.fragment_home_turista.*
import ort.tp3_login.R
import ort.tp3_login.adapters.ServicioAdapter
import ort.tp3_login.dataclasses.*
import ort.tp3_login.entities.ServicioCard
import ort.tp3_login.services.RetrofitInstance
import ort.tp3_login.viewModels.ViewModelHomeTurista
import retrofit2.Response


class home_turista : Fragment() {

    //Vista
    lateinit var view1 : View
    val DEFAULT_MAX_RESULTS: Int = 50
    val DEFAULT_MAX_KM: Double = 25.0
    //ViewModel
    private val viewModel: ViewModelHomeTurista by activityViewModels()

    //RecyclerView
    lateinit var recyclerView: RecyclerView
    var cardsTuristaLista : MutableList<ServicioCard> = ArrayList<ServicioCard>()
    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var adapter: ServicioAdapter

    //Buscar
    lateinit var TextViewCategories: TextView
    lateinit var buscarButton: Button
    var selectedCategorie: ArrayList<Boolean> = ArrayList<Boolean>()
    var categorieListInt : ArrayList<Int> = ArrayList<Int>()
    lateinit var resultCategorie: MutableListIterator<CategoriaItem>
    var categoriesNombre : ArrayList<String> = ArrayList<String>()
    lateinit var dialog : AlertDialog
    lateinit var maxKm : TextView
    var categoriesParaBackend : ArrayList<String> = ArrayList<String>()
    var categoriesAux : MutableMap<String,CategoriaItem> = HashMap()


    private fun fetchCategories(){
        val retService : ServicioService = RetrofitInstance
            .getRetrofitInstance()
            .create(ServicioService::class.java)
        val responseLiveData : LiveData<Response<Categorias>> = liveData{
            val response = retService.getCategories(viewModel.token)
            Log.d("Categorias", response.body().toString())
            emit(response)
        }
        responseLiveData.observe(viewLifecycleOwner,Observer{
            val categoriasList = it.body()?.listIterator()
            if (categoriasList != null) {
                //Log.d("categoriasList", categoriasList.toString())
                viewModel.categorias = categoriasList
                resultCategorie = viewModel.categorias


            }else{
                Log.d("categoriasList","es null")
            }
        })

    }


    //Location


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view1 = inflater.inflate(R.layout.fragment_home_turista, container, false)
        TextViewCategories = view1.findViewById(R.id.textViewCategories)
        //buscarButton = view1.findViewById(R.id.buscarButton)
        recyclerView = view1.findViewById(R.id.recyclerViewHomeTurista)
        maxKm = view1.findViewById(R.id.editTextTextRadio)
        buscarButton = view1.findViewById(R.id.button12)
        fetchCategories()

        TextViewCategories.setOnClickListener {
            createDialog()
        }


        buscarButton.setOnClickListener {
            fetchActivities()


            Log.d("response","Click Click")
         }



        return view1
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.lista.observe(viewLifecycleOwner, Observer{result ->

            cardsTuristaLista = result
            //configuraciòn obligatoria recyclerview
            recyclerView.hasFixedSize()
            linearLayoutManager = LinearLayoutManager(context)
            recyclerView.layoutManager = linearLayoutManager

            //setear adapter
            adapter = ServicioAdapter(cardsTuristaLista){x ->
                onItemClick(x)
            }
            //asignar adaptar a recyclerview
            recyclerView.adapter = adapter
        })

    }



    //setear mi locaciòn y pedir permisos (permisos estan en el manifest file)



    override fun onStart() {
        super.onStart()
        viewModel.loadActivities()
        Log.d("usuario en Home turista", viewModel.user.value.toString())

    }

    // funcion que vamos a usar para ir a la pantalla de detalles
    private fun onItemClick(position: Int) : Boolean {
        view1.findNavController().navigate(R.id.action_home_turista_to_detalleActividad)
        //Snackbar.make(view1,"vamos a detalle de $position", Snackbar.LENGTH_SHORT).show()
        return true
    }

    private fun createDialog(){
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


            categoriasArray.forEachIndexed { index, s ->
                if (sparseBooleanArray.get(index, false)) {
                    categoriesAux[s]?.let { categoriesParaBackend.add(it.id) }
                    Log.d("Categoria --> IF ID" , categoriesParaBackend[counter].toString())
                    counter += 1
                }
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

    private fun fetchActivities() {
        val retService: ServicioService = RetrofitInstance
            .getRetrofitInstance()
            .create(ServicioService::class.java)
        val responseLiveData: LiveData<Response<Servicios>> = liveData {
            if(maxKm.text.toString().isEmpty()){
                maxKm.setText(DEFAULT_MAX_KM.toString())
            }
            val servicioSearch : ServiciosSearch = ServiciosSearch(
                viewModel.myLatitude,
                viewModel.myLongitude,
                maxKm.text.toString().toDouble(),
                DEFAULT_MAX_RESULTS,
                categoriesParaBackend
            )

            Log.d("response -->ServiciosSearch", servicioSearch.toString())
            Log.d("response -->token", viewModel.token)
            val response = retService.searchServicios(servicioSearch, viewModel.token)

            emit(response)
        }



        responseLiveData.observe(viewLifecycleOwner,Observer {
            val serviciosList = it.body()
            if (serviciosList != null) {
                Log.d("response -->Servicios", serviciosList.toString())
                viewModel.actividades.value = serviciosList

            }
            viewModel.loadActivities()
        })

//        responseLiveData.observe( Observer<Response<Servicios>?> {
//            val serviciosList = it.body()
//            if (serviciosList != null) {
//                viewModel.actividades.value = serviciosList
//
//                Log.d("serviciosList", serviciosList.toString())
//                Log.d("serviciosList --> Viewmodel", viewModel.actividades.value.toString())
//            } else {
//                Log.d("serviciosList", "es null")
//            }
//        })


        //TODO Implementar viewModel.loadActivities()

    }


}


