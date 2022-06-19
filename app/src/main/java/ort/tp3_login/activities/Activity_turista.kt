package ort.tp3_login.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_turista.*
import ort.tp3_login.R
import androidx.lifecycle.Observer
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import ort.tp3_login.dataclasses.*
import ort.tp3_login.services.RetrofitInstance
import ort.tp3_login.viewModels.ViewModelHomeTurista
import retrofit2.Response


class activity_turista : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    val DEFAULT_MAX_KM: Double = 25.0
    val DEFAULT_MAX_RESULTS: Int = 50
    var gson: Gson = Gson()

    val viewModel: ViewModelHomeTurista by viewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var myLongitude: Double = 0.0
    var myLatitude: Double = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_turista)

        Log.d("dentro el metodo", "onCreate de Home_turista")

        viewModel.user.value =
            gson.fromJson(intent.getStringExtra("user"), UsuarioLogin::class.java)
        setmylocation()
        getReservas()
        viewModel.token = intent.getStringExtra("token").toString()

        Log.d("token --> dentro el metodo", viewModel.token)
        //navController = Navigation.findNavController(this,R.id.nav_host_fragment)
        val nav_host_fragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = nav_host_fragment.navController

        nav_view.getHeaderView(0).findViewById<TextView>(R.id.user_name).text =
            viewModel.user.value!!.firstName + " " + viewModel.user.value!!.lastName
        nav_view.getHeaderView(0).findViewById<TextView>(R.id.user_email).text =
            viewModel.user.value!!.email
        Picasso.get().load(viewModel.user.value?.photoUrl?.toUri()).into(nav_view.getHeaderView(0).findViewById<CircleImageView>(R.id.profile_image))
        if(nav_view.getHeaderView(0).findViewById<CircleImageView>(R.id.profile_image).drawable == null){
            nav_view.getHeaderView(0).findViewById<CircleImageView>(R.id.profile_image).setImageResource(R.drawable.icon_profile)
        }


        nav_view.setupWithNavController(navController)
        NavigationUI.setupActionBarWithNavController(this, navController, drawer_layout_id)

        nav_view.menu.findItem(R.id.logout).setOnMenuItemClickListener { menuItem ->
            Log.d("menuItem", menuItem.toString())
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            true
        }
        //google places
        //Places.initialize(this, BuildConfig.MAPS_API_KEY)


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
        return NavigationUI.navigateUp(navController, drawer_layout_id)
    }


    @SuppressLint("MissingPermission")
    private fun setmylocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    myLatitude = location!!.latitude
                    myLongitude = location!!.longitude
                    Log.d("longitude", myLongitude.toString())
                    Log.d("latitude", myLatitude.toString())
                }.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        fetchActivities()
                    } else {
                        Log.d("latitude", "No funco")
                    }
                }
        } else {
            ActivityCompat.requestPermissions(
                this as Activity,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
        }
    }


    private fun fetchActivities() {
        val retService: ServicioService = RetrofitInstance
            .getRetrofitInstance()
            .create(ServicioService::class.java)
        val responseLiveData: LiveData<Response<Servicios>> = liveData {
            var servicioSearch : ServiciosSearch?
            val favoritas = viewModel.user.value?.favCategories
            if(favoritas == null){
            servicioSearch  = ServiciosSearch(
                myLatitude,
                myLongitude,
                DEFAULT_MAX_KM,
                DEFAULT_MAX_RESULTS,
                listOf()
            )}else{
                val favoritasIds = arrayListOf<String>()

                favoritas.forEach {
                    favoritasIds.add(it.id)

                }
                servicioSearch = ServiciosSearch(
                    myLatitude,
                    myLongitude,
                    DEFAULT_MAX_KM,
                    DEFAULT_MAX_RESULTS,
                    favoritasIds
                )
            }
            viewModel.myLatitude = myLatitude
            viewModel.myLongitude = myLongitude
            Log.d("response -->ServiciosSearch", servicioSearch.toString())
            Log.d("response -->token", viewModel.token)
            val response = retService.searchServicios(servicioSearch, viewModel.token)

            emit(response)
        }
        responseLiveData.observe(this, Observer<Response<Servicios>?> {
            val serviciosList = it.body()
            if (serviciosList != null) {
                viewModel.actividades.value = serviciosList

                Log.d("serviciosList", serviciosList.toString())
                Log.d("serviciosList --> Viewmodel", viewModel.actividades.value.toString())
            } else {
                Log.d("serviciosList", "es null")
            }
            viewModel.loadActivities()


        })

    }


    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_drawable_turista,menu)
        return true
    }*/
    private fun getReservas() {
        val retService: ServicioService = RetrofitInstance
            .getRetrofitInstance()
            .create(ServicioService::class.java)
        val responseLiveData: LiveData<Response<List<Reserva>>> = liveData {
            val response = retService.getReservas(viewModel.token)

            emit(response)
        }

        responseLiveData.observe(this,Observer {
            val reservasList = it.body()
            if (reservasList != null) {

                viewModel.reservas.value = reservasList as MutableList<Reserva>?
                Log.d("response -->Servicios", viewModel.reservas.value.toString())

            }
            viewModel.loadReservas()
        })

    }

}