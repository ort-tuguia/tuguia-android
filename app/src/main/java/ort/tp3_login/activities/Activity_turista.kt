package ort.tp3_login.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_turista.*
import ort.tp3_login.R
import ort.tp3_login.dataclasses.Servicios
import ort.tp3_login.dataclasses.ServicioService
import androidx.lifecycle.Observer
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import ort.tp3_login.services.RetrofitInstance
import ort.tp3_login.viewModels.ViewModelHomeTurista
import retrofit2.Response


class activity_turista : AppCompatActivity() {

    private lateinit var navController : NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    val DEFAULT_MAX_KM : Double = 10.0
    val DEFAULT_MAX_RESULTS : Int = 50


    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var myLongitude : Double = 0.0
    var myLatitude : Double = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_turista)

        Log.d("dentro el metodo","onCreate de Home_turista")
        val viewModel : ViewModelHomeTurista by viewModels()
        setmylocation ()
        fetchActivities ()
        //navController = Navigation.findNavController(this,R.id.nav_host_fragment)
        val nav_host_fragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = nav_host_fragment.navController

        nav_view.setupWithNavController(navController)
        NavigationUI.setupActionBarWithNavController(this,navController,drawer_layout_id)

        /*appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(),
               /* R.id.home_turista,
                R.id.perfil_turista,
                R.id.favoritos_turista,
                R.id.mapa_turista
            ),*/
            fallbackOnNavigateUpListener = ::onSupportNavigateUp
        )

       setupActionBarWithNavController(navController,appBarConfiguration)
        navController.addOnDestinationChangedListener { _, _, _ ->
                supportActionBar?.setHomeAsUpIndicator(R.drawable.hamburger)
        }*/

    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController,drawer_layout_id)
    }


    @SuppressLint("MissingPermission")
    private fun setmylocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED){
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                    myLatitude = location!!.latitude
                    myLongitude = location!!.longitude
                    Log.d("longitude",myLongitude.toString())
                    Log.d("latitude",myLatitude.toString())
                }
        }else{
            ActivityCompat.requestPermissions(
                this as Activity,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
        }
    }

    private fun fetchActivities() {
        val retService : ServicioService = RetrofitInstance
            .getRetrofitInstance()
            .create(ServicioService::class.java)
        val responseLiveData : LiveData<Response<Servicios>> = liveData{
            val response = retService.searchServicios(myLatitude,myLongitude,DEFAULT_MAX_KM,DEFAULT_MAX_RESULTS)
            Log.d("response", response.toString())
            emit(response)
        }
        responseLiveData.observe(this,Observer{
            val serviciosList = it.body()?.listIterator()
            if (serviciosList != null) {
                    Log.d("serviciosList", serviciosList.toString())
            }else{
                Log.d("serviciosList","es null")
            }
        })
    }




    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_drawable_turista,menu)
        return true
    }*/








}