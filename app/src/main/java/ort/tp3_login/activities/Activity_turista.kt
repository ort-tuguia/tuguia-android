package ort.tp3_login.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_turista.*
import ort.tp3_login.R


class activity_turista : AppCompatActivity() {

    private lateinit var navController : NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_turista)

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

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_drawable_turista,menu)
        return true
    }*/








}