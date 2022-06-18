package ort.tp3_login.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ort.tp3_login.R
import ort.tp3_login.adapters.ReviewsAdapter
import ort.tp3_login.adapters.ServicioAdapter
import ort.tp3_login.dataclasses.Reserva
import ort.tp3_login.dataclasses.Review
import ort.tp3_login.dataclasses.ServicioService
import ort.tp3_login.entities.ReviewCard
import ort.tp3_login.services.RetrofitInstance
import ort.tp3_login.viewModels.ViewModelGuia
import retrofit2.Response


class ReviewsActividadGuia : Fragment() {
    lateinit var view1 : View


    //ViewModel
    private val viewModel: ViewModelGuia by activityViewModels()

    //RecyclerView
    lateinit var recyclerView: RecyclerView
    var cardsReviewsLista : MutableList<ReviewCard> = ArrayList<ReviewCard>()
    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var adapter: ReviewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view1= inflater.inflate(R.layout.fragment_reviews_guia, container, false)
        recyclerView = view1.findViewById(R.id.recyclerViewReviews)
        getReviews()
        return view1
    }

    override fun onStart() {
        super.onStart()

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.listaReviews.observe(viewLifecycleOwner, Observer{result ->

            cardsReviewsLista = result
            //configuraciòn obligatoria recyclerview
            recyclerView.hasFixedSize()
            linearLayoutManager = LinearLayoutManager(context)
            recyclerView.layoutManager = linearLayoutManager

            //setear adapter
            adapter = ReviewsAdapter(cardsReviewsLista){x ->

            }
            //asignar adaptar a recyclerview
            recyclerView.adapter = adapter
        })

    }

    private fun getReviews() {
        val retService: ServicioService = RetrofitInstance
            .getRetrofitInstance()
            .create(ServicioService::class.java)
        val responseLiveData: LiveData<Response<List<Review>>> = liveData {
            val actividadId = viewModel.servicioItemSeleccionado!!.id
            val response = retService.getReviewsActividad(actividadId,viewModel.token)
            Log.d("response Review Actividad", response.toString())
            emit(response)
        }

        responseLiveData.observe(viewLifecycleOwner,Observer {
            val reviewsList = it.body()
            if (reviewsList != null) {

                viewModel.reviews.value = reviewsList as MutableList<Review>?
                Log.d("response -->Reviews", viewModel.reviews.value.toString())

            }
            viewModel.loadReviews()
        })

    }






}

