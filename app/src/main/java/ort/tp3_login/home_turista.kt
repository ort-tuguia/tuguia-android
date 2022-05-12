package ort.tp3_login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class home_turista : Fragment() {


    lateinit var view1 : View
    lateinit var recyclerView: RecyclerView

    var nombreGuiaList: ArrayList<String> = ArrayList()
    var tituloList: ArrayList<String> = ArrayList()
    var imagenList: ArrayList<Int> = ArrayList()
    var perfilPicList:ArrayList<Int> = ArrayList()
    var fechaList : ArrayList<String> = ArrayList()
    var valoracionList: ArrayList<String> = ArrayList()

    lateinit var adapter: ServicioAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view1 = inflater.inflate(R.layout.fragment_home_turista, container, false)
        //var nombreDeUsuario = requireArguments().getString("usuario")
        //textHola.text = "Hola... ${nombreDeUsuario.toString()}"

        recyclerView = view1.findViewById(R.id.recyclerViewHomeTurista)
        recyclerView.layoutManager = LinearLayoutManager(view1.context)

        tituloList.add("Free walking tour Missiones")
        tituloList.add("Cataratas de Iguazu")

        nombreGuiaList.add("Tom Maenhout")
        nombreGuiaList.add("Tommy")

        imagenList.add(R.drawable.recycler_city)
        imagenList.add(R.drawable.recycler_iguazu)

        perfilPicList.add(R.drawable.profile)
        perfilPicList.add(R.drawable.icon_profile)

        fechaList.add("24/10/2022")
        fechaList.add("25/02/2023")

        valoracionList.add("5.0")
        valoracionList.add("4.0")

        adapter = ServicioAdapter(
            nombreGuiaList,
            tituloList,
            imagenList,
            perfilPicList,
            fechaList,
            valoracionList,
            view1.context)

        recyclerView.adapter = adapter

        return view1
    }




    override fun onStart() {
        super.onStart()

        /*textViewVolver.setOnClickListener {
            val action1 = home_turistaDirections.actionHomeTuristaToLogin()
            view1.findNavController().navigate(action1)*/
    }
}


