package ort.tp3_login.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import ort.tp3_login.R

class SplashActivity : AppCompatActivity() {

    companion object {
        private const val SPLASH_TIME_OUT:Long = 2000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)



        Handler().postDelayed(
            {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            , SPLASH_TIME_OUT)
    }
}