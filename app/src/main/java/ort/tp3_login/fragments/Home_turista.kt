package ort.tp3_login.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import ort.tp3_login.R
import ort.tp3_login.adapters.ServicioAdapter
import ort.tp3_login.entities.ServicioCard
import ort.tp3_login.viewModels.ViewModelHomeTurista


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
    lateinit var buscareditText: EditText
    lateinit var buscarButton: Button


    //Location


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view1 = inflater.inflate(R.layout.fragment_home_turista, container, false)
        //buscareditText = view1.findViewById(R.id.buscarTextinput)
        //buscarButton = view1.findViewById(R.id.buscarButton)
        recyclerView = view1.findViewById(R.id.recyclerViewHomeTurista)

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


    }

    // funcion que vamos a usar para ir a la pantalla de detalles
    private fun onItemClick(position: Int) : Boolean {
        Snackbar.make(view1,"vamos a detalle de $position", Snackbar.LENGTH_SHORT).show()
        return true
    }
}


