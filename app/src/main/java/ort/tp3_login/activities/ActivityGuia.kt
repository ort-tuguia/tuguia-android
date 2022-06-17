package ort.tp3_login.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_guia.*
import kotlinx.android.synthetic.main.activity_turista.*
import ort.tp3_login.R
import ort.tp3_login.dataclasses.Categorias
import ort.tp3_login.dataclasses.ServicioService
import ort.tp3_login.dataclasses.Servicios
import ort.tp3_login.dataclasses.UsuarioLogin
import ort.tp3_login.services.RetrofitInstance
import ort.tp3_login.viewModels.ViewModelGuia
import retrofit2.Response


class ActivityGuia : AppCompatActivity() {
    private lateinit var navController : NavController
    val viewModel : ViewModelGuia by viewModels()
    var gson : Gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guia)
        viewModel.user.value= gson.fromJson(intent.getStringExtra("user"), UsuarioLogin::class.java)

        viewModel.user.value = gson.fromJson(intent.getStringExtra("user"), UsuarioLogin::class.java)
        viewModel.token = intent.getStringExtra("token").toString()

        val nav_host_fragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_guia) as NavHostFragment
        navController = nav_host_fragment.navController

        nav_view_guia.getHeaderView(0).findViewById<TextView>(R.id.user_name).text = viewModel.user.value!!.firstName + " " + viewModel.user.value!!.lastName
        nav_view_guia.getHeaderView(0).findViewById<TextView>(R.id.user_email).text = viewModel.user.value!!.email
       Picasso.get().load(viewModel.user.value?.photoUrl?.toUri()).into(nav_view_guia.getHeaderView(0).findViewById<CircleImageView>(R.id.profile_image))

        nav_view_guia.setupWithNavController(navController)
        NavigationUI.setupActionBarWithNavController(this,navController,drawer_layout_id_guia)

        nav_view_guia.menu.findItem(R.id.logout_guia).setOnMenuItemClickListener { menuItem ->
            Log.d("menuItem", menuItem.toString())
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            true
        }
        fetchActivities()
    }


        private fun fetchActivities(){
            val retService : ServicioService = RetrofitInstance
                .getRetrofitInstance()
                .create(ServicioService::class.java)
            val responseLiveData : LiveData<Response<Servicios>> = liveData{
                val response = retService.getMyServicios(viewModel.token)
                Log.d("servicios", response.body().toString())
                emit(response)
            }
            responseLiveData.observe(this, Observer{
                val serviciosList = it.body()
                if (serviciosList != null) {
                    //Log.d("categoriasList", categoriasList.toString())
                    viewModel.actividades.value = serviciosList
                    //activitiesList = viewModel.categorias
                    Log.d("serviciosList", serviciosList.toString())
                    Log.d("serviciosList --> Viewmodel", viewModel.actividades.value.toString())
                }else{
                    Log.d("servicioList","es null")
                }
                viewModel.loadActivities()
            })

        }


    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController,drawer_layout_id_guia)
    }
}