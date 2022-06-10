package ort.tp3_login.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.net.toUri
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
import ort.tp3_login.dataclasses.UsuarioLogin
import ort.tp3_login.viewModels.ViewModelGuia


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
       // Picasso.get().load(viewModel.user.value?.photoUrl?.toUri()).into(nav_view.getHeaderView(0).findViewById<CircleImageView>(R.id.profile_image))

        nav_view_guia.setupWithNavController(navController)
        NavigationUI.setupActionBarWithNavController(this,navController,drawer_layout_id_guia)

        nav_view_guia.menu.findItem(R.id.logout_guia).setOnMenuItemClickListener { menuItem ->
            Log.d("menuItem", menuItem.toString())
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController,drawer_layout_id_guia)
    }
}