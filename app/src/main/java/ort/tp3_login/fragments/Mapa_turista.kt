package ort.tp3_login.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import ort.tp3_login.R
import ort.tp3_login.dataclasses.ServicioItem
import ort.tp3_login.viewModels.ViewModelGuia
import ort.tp3_login.viewModels.ViewModelHomeTurista

class mapa_turista : Fragment() {

    lateinit var mapFragment: View
    lateinit var map: GoogleMap
    private  val viewModel: ViewModelHomeTurista by activityViewModels()
    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        val buenosAires = LatLng(-34.56660241116843, -58.44412629436163)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(buenosAires, 15f))
        googleMap.uiSettings.apply {
            isZoomControlsEnabled = true
            isMyLocationButtonEnabled = true

        }
        map = googleMap
        checkLocationPermission()

        var listaActividades : MutableLiveData<MutableList<ServicioItem>> = viewModel.actividades
        listaActividades.observe(viewLifecycleOwner, Observer{
            it.forEach {
                val location = LatLng(it.locationLatitude, it.locationLongitude)
                googleMap.addMarker(MarkerOptions().position(location).title("${it.name}"))
            }
        })

    }


    @SuppressLint("MissingPermission")
    private fun checkLocationPermission(){
        if(ContextCompat.checkSelfPermission(mapFragment.context,android.Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED){
            map.isMyLocationEnabled = true
            //Snackbar.make(mapFragment,"Tenemos permiso",Snackbar.LENGTH_SHORT).show()
        }else{
          requestPermission()
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            mapFragment.context as Activity,
            arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
            1

        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mapFragment = inflater.inflate(R.layout.fragment_mapa_turista, container, false)
        return mapFragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    @Deprecated("Deprecated in Java")
    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,

    ) {
        if(requestCode != 1){
            return
        }
        if(grantResults.isNotEmpty()&&grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Snackbar.make(mapFragment,"acceso aprobado",Snackbar.LENGTH_SHORT).show()
            map.isMyLocationEnabled = true
        }else{
            Snackbar.make(mapFragment,"Necesitamos su permiso",Snackbar.LENGTH_SHORT).show()
        }
    }
}

