package ort.tp3_login.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ort.tp3_login.R


class Reviews : Fragment() {
    lateinit var view1 : View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view1= inflater.inflate(R.layout.fragment_reviews, container, false)
        return view1
    }




}