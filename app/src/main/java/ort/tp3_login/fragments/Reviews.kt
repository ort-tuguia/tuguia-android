package ort.tp3_login.fragments

import android.os.Bundle
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
import ort.tp3_login.R
import ort.tp3_login.adapters.ReviewsAdapter
import ort.tp3_login.adapters.ServicioAdapter
import ort.tp3_login.dataclasses.Review
import ort.tp3_login.entities.ReviewCard
import ort.tp3_login.entities.ServicioCard
import ort.tp3_login.viewModels.ViewModelHomeTurista


class Reviews : Fragment() {
    lateinit var view1 : View

    //ViewModel
    private val viewModel: ViewModelHomeTurista by activityViewModels()

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
        view1= inflater.inflate(R.layout.fragment_reviews, container, false)
        recyclerView = view1.findViewById(R.id.recyclerViewReviews)
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




}