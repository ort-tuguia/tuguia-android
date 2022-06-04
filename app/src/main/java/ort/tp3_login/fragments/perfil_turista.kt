package ort.tp3_login.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import ort.tp3_login.R
import ort.tp3_login.viewModels.ViewModelHomeTurista


class perfil_turista : Fragment() {
    lateinit var v: View
    private  val viewModel: ViewModelHomeTurista by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_perfil_turista, container, false)
        v.findViewById<TextView>(R.id.user_name).text = viewModel.user.value?.firstName + " " + viewModel.user.value?.lastName
        v.findViewById<TextView>(R.id.user_email).text = viewModel.user.value?.email
        return  v
    }


}