package ort.tp3_login.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import ort.tp3_login.R
import ort.tp3_login.adapters.ServicioAdapter
import ort.tp3_login.entities.ServicioCard


class home_turista : Fragment() {


    lateinit var view1 : View


    lateinit var recyclerView: RecyclerView
    var cardsTuristaLista : MutableList<ServicioCard> = ArrayList<ServicioCard>()
    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var adapter: ServicioAdapter

    lateinit var buscareditText: EditText
    lateinit var buscarButton: Button


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




    override fun onStart() {
        super.onStart()

        //llenamos la lista
        cardsTuristaLista.add(ServicioCard(
            "Tom Maenhout",
            "24/10/2022",
            "free walking tour Missiones",
            R.drawable.profile,
            5,
            R.drawable.recycler_city,
            "city trip"))

        cardsTuristaLista.add(ServicioCard(
            "Tommy",
            "13/10/2022",
            "Cataratas de Iguazu",
            R.drawable.icon_profile,
            4,
            R.drawable.recycler_iguazu,
            "naturaleza"))

        //configuraciòn obligatoria
        recyclerView.hasFixedSize()
        linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager

        adapter = ServicioAdapter(cardsTuristaLista){x ->
            onItemClick(x)
        }

        recyclerView.adapter = adapter
    }
    private fun onItemClick(position: Int) : Boolean {
        Snackbar.make(view1,"vamos a detalle de $position", Snackbar.LENGTH_SHORT).show()
        return true
    }
}


