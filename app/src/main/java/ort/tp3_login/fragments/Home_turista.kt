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
import kotlinx.android.synthetic.main.fragment_home_turista.*
import ort.tp3_login.R
import ort.tp3_login.adapters.ServicioAdapter
import ort.tp3_login.dataclasses.CategoriaItem
import ort.tp3_login.dataclasses.Categorias
import ort.tp3_login.dataclasses.ServicioService
import ort.tp3_login.entities.ServicioCard
import ort.tp3_login.services.RetrofitInstance
import ort.tp3_login.viewModels.ViewModelHomeTurista
import retrofit2.Response


class home_turista : Fragment() {

    //Vista
    lateinit var view1 : View

    //ViewModel
    private  val viewModel: ViewModelHomeTurista by activityViewModels()

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
                resultCategorie = categoriasList


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

        fetchCategories()

        TextViewCategories.setOnClickListener {
            createDialog()
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
        resultCategorie.forEach { categoria ->
            categoriesNombre.add(categoria.name)
            categoriesAux[categoria.name]= categoria
        }
        if (selectedCategorie.isEmpty()){
            repeat(categoriesNombre.count()) {selectedCategorie.add(false)}
        }
        val categoriasArray: Array<String> = categoriesNombre.toTypedArray()


        builder.setTitle("Seleccione categorias")
        builder.setMultiChoiceItems(
            categoriasArray,
            selectedCategorie.toBooleanArray()
        ) { dialog, which, isChecked ->}
        builder.setPositiveButton("Submit"){dialog,which->
            val alertDialog = dialog as AlertDialog
            val sparseBooleanArray = alertDialog.listView.checkedItemPositions
            var counter = 0

            textViewSelect.text = ""
            categoriasArray.forEachIndexed { index, s ->
                if (sparseBooleanArray.get(index, false)) {
                    categoriesAux[s]?.let { categoriesParaBackend.add(it.id) }
                    Log.d("Categoria --> IF ID" , categoriesParaBackend[counter].toString())

                    //textViewSelect.append("\n$s")
                    counter += 1
                }
            }

            if (counter > 0) {


           }
        }
            builder.setNeutralButton("Cancel"){dialog,which->
                textViewSelect.text = ""
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
                        selectedCategorie[index] = true
                    }else{
                        selectedCategorie[index] = false
                    }
                }

                dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                    .isEnabled = checkedItems >=2
            }

    }
}


