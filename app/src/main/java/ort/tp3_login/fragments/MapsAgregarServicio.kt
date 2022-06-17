package ort.tp3_login.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import ort.tp3_login.R
import ort.tp3_login.viewModels.ViewModelGuia

class MapsAgregarServicio : Fragment() {

    lateinit var mapFragment: View
    lateinit var map: GoogleMap
    private val viewModel: ViewModelGuia by activityViewModels()
    private lateinit var location : LatLng
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
    }

    private fun openDialogConfirmarUbicacion() {
        var alertDialog = AlertDialog.Builder(this.context)
        alertDialog.setTitle("Locación")
            .setMessage("Querés usar la locación siguiente: lat: ${location.latitude} lon: ${location.longitude}\"?")
            .setIcon(R.drawable.icon_agregar_ubicacion)
            .setCancelable(false)
            .setNegativeButton("No",DialogInterface.OnClickListener{dialog: DialogInterface?, which: Int ->
                map.clear()
                dialog?.cancel()
            })
            .setPositiveButton("Si",DialogInterface.OnClickListener{dialog: DialogInterface?, which: Int ->
                map.clear()
                viewModel.servicioLocationlat = location.latitude
                viewModel.servicioLocationlon = location.longitude
                if(viewModel.servicioItemSeleccionado != null){
                    openAlertDialogImagen()
                }else{
                    openAlertDialog()
                }

            })
        alertDialog.create().show()
    }
    private fun openAlertDialogImagen() {
        var alertDialog = AlertDialog.Builder(this.context)
        alertDialog.setTitle("Imagen")
            .setMessage("Querés cambiar el imagen de la actividad?")
            .setPositiveButton("Si",
                DialogInterface.OnClickListener{ dialog: DialogInterface?, which: Int ->
                    //mapFragment.findNavController().navigate(R.id.action_mapsAgregarServicio_to_fotoAgregarServicio)
                })
            .setNegativeButton("No",
                DialogInterface.OnClickListener{ dialog: DialogInterface?, which: Int ->
                    //TODO implementar viewmodel url imagen
                    //mapFragment.findNavController().navigate(R.id.action_mapsAgregarServicio_to_agregarServicio)
                })
        alertDialog.create().show()
    }

    private fun openAlertDialog() {
        var alertDialog = AlertDialog.Builder(this.context)
        alertDialog.setTitle("Imagen")
            .setMessage("Eliga una imagen para su actividad")
            .setIcon(R.drawable.icon_agregar_imagen)
            .setCancelable(false)
            .setPositiveButton("Ok",
                DialogInterface.OnClickListener{ dialog: DialogInterface?, which: Int ->
                    //mapFragment.findNavController().navigate(R.id.action_mapsAgregarServicio_to_fotoAgregarServicio)
                })
        alertDialog.create().show()
    }


    @SuppressLint("MissingPermission")
    private fun checkLocationPermission(){
        if(ContextCompat.checkSelfPermission(mapFragment.context,android.Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED){
            map.isMyLocationEnabled = true
            Snackbar.make(mapFragment,"Tenemos permiso", Snackbar.LENGTH_SHORT).show()
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
            Snackbar.make(mapFragment,"acceso aprobado", Snackbar.LENGTH_SHORT).show()
            map.isMyLocationEnabled = true
        }else{
            Snackbar.make(mapFragment,"Necesitamos su permiso", Snackbar.LENGTH_SHORT).show()
        }
    }
}