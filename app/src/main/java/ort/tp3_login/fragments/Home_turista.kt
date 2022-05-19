package ort.tp3_login.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import ort.tp3_login.R
import ort.tp3_login.adapters.ServicioAdapter
import ort.tp3_login.entities.ServicioCard
import ort.tp3_login.viewModels.ViewModelHomeTurista


class home_turista : Fragment() {

    //Vista
    lateinit var view1 : View

    //ViewModel
    private lateinit var viewModel: ViewModelHomeTurista

    //RecyclerView
    lateinit var recyclerView: RecyclerView
    var cardsTuristaLista : MutableList<ServicioCard> = ArrayList<ServicioCard>()
    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var adapter: ServicioAdapter

    //Buscar
    lateinit var buscareditText: EditText
    lateinit var buscarButton: Button


    //Locación
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var myLongitude : Double = 0.0
    var myLatitude : Double = 0.0


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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(view1.context)
        setmylocation ()
        viewModel = ViewModelProvider(requireActivity()).get(ViewModelHomeTurista::class.java)
        viewModel.lista.observe(viewLifecycleOwner, Observer{result ->
            cardsTuristaLista = result
        })
    }

    @SuppressLint("MissingPermission")
    private fun setmylocation() {
        if(ContextCompat.checkSelfPermission(view1.context,android.Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED){
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                    myLatitude = location!!.longitude
                    myLongitude = location!!.latitude
                    Log.d("longitude",myLongitude.toString())
                    Log.d("latitude",myLatitude.toString())
                }
        }else{
            ActivityCompat.requestPermissions(
                view1.context as Activity,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
        }
    }


    override fun onStart() {
        super.onStart()

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


