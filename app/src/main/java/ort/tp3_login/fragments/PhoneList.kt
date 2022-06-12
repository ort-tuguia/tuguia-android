package ort.tp3_login.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.phone_design.view.*
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.runBlocking
import ort.tp3_login.R
import ort.tp3_login.adapters.PhonesAdapter
import ort.tp3_login.dataclasses.Phone
import ort.tp3_login.dataclasses.ServicioService
import ort.tp3_login.services.RetrofitInstance
import ort.tp3_login.viewModels.ViewModelHomeTurista

class PhoneList : Fragment() {
    var cardsPhonesList : List<Phone> = ArrayList<Phone>()
    lateinit var adapter : PhonesAdapter
    lateinit var v: View
    lateinit var recyclerView: RecyclerView
    lateinit var buttonSave : Button
    lateinit var buttonAddPhone : Button
    private val viewModel: ViewModelHomeTurista by activityViewModels()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private  var service: ServicioService = RetrofitInstance
        .getRetrofitInstance()
        .create(ServicioService::class.java)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v= inflater.inflate(R.layout.fragment_phone_list, container, false)
        recyclerView = v.findViewById(R.id.recyclerViewPhoneList)
        buttonSave = v.findViewById(R.id.buttonSave)
        buttonAddPhone = v.findViewById(R.id.buttonAddPhone)




        return v

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()

        buttonSave.setOnClickListener {
            viewModel.user.value!!.phones = adapter.getData()
            fetcher()
            setRecyclerView()
        }
        buttonAddPhone.setOnClickListener {
            v.findNavController().navigate(R.id.action_phoneList_to_telefonosTurista)
        }


    }
    fun setRecyclerView() {
        cardsPhonesList = viewModel.user.value!!.phones
        recyclerView.hasFixedSize()
        linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager
        adapter = PhonesAdapter(cardsPhonesList as MutableList<Phone>){
            Log.d("Phone",   recyclerView.layoutManager.toString())
            Log.d("Phone",   recyclerView.adapter.toString())
        }
        recyclerView.adapter = adapter
    }
    fun fetcher() = runBlocking(CoroutineName("fetcher")) {
        var response = service.putPhone(viewModel.user.value!!.phones as ArrayList<Phone>, viewModel.token)
        Log.d("Phone", response.body().toString())
        viewModel.user.value = response.body()
    }


}