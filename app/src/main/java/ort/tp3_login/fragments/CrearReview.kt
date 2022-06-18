package ort.tp3_login.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar

import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import ort.tp3_login.R
import ort.tp3_login.dataclasses.CrearReview
import ort.tp3_login.dataclasses.CrearServicio
import ort.tp3_login.dataclasses.Photo
import ort.tp3_login.dataclasses.ServicioService
import ort.tp3_login.services.RetrofitInstance
import ort.tp3_login.viewModels.ViewModelHomeTurista


class CrearReview : Fragment() {

    lateinit var view1 : View
    lateinit var buttonMandarValoración: Button
    lateinit var reviewText : EditText
    private val viewModel : ViewModelHomeTurista by activityViewModels()
    lateinit var ratingBar: RatingBar
    lateinit var review : CrearReview
    var valoracion : Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view1 = inflater.inflate(R.layout.fragment_rating_bar, container, false)
        ratingBar = view1.findViewById(R.id.ratingBar)
        ratingBar.rating = 2.5f
        ratingBar.stepSize = .5f
        buttonMandarValoración = view1.findViewById(R.id.buttonRating)
        reviewText = view1.findViewById(R.id.editTextReview)

        return view1
    }

    override fun onStart() {
        super.onStart()
        ratingBar.setOnRatingBarChangeListener{ratingBar, rating, fromUser ->
            valoracion = rating.toDouble()

        }
        buttonMandarValoración.setOnClickListener {
            review = CrearReview(
                reviewText.text.toString(),
                valoracion.toString().toDouble()
            )
            Log.d("reviewCreate", review.comment)
            var statusCode : Boolean = fetcherCrearReview()
            if (statusCode) {

                Snackbar.make(view1, "Gracias por dejar un Review", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(Color.parseColor("#42D727"))
                    .show()
            }

            view1.findNavController().navigate(R.id.action_crearReview_to_home_turista)
        }
    }

    private suspend fun crearReview(): Boolean {
        val retService: ServicioService = RetrofitInstance
            .getRetrofitInstance()
            .create(ServicioService::class.java)

        val response = retService.postReview(viewModel.reservaSeleccionado.id,review, viewModel.token)
        Log.d("reviewCrearReview", review.toString())
        if (response.isSuccessful) {
            return true
        }
        val jObjError = JSONObject(response.errorBody()!!.string())
        Log.d("Error", jObjError.getString("message"))
        Snackbar.make(view1, jObjError.getString("message"), Snackbar.LENGTH_SHORT)
            .setBackgroundTint(Color.parseColor("#D72F27"))
            .show()
        return false
    }

    fun fetcherCrearReview() = runBlocking(CoroutineName("fetcherCrearReserva")) {
        Log.d("reviewFetcher", review.toString())
        crearReview()
    }



}