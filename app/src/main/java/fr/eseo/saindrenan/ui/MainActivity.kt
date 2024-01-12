package fr.eseo.saindrenan.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import com.google.android.material.snackbar.Snackbar
import fr.eseo.saindrenan.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Snackbar.make(findViewById(android.R.id.content), "Bienvenu sur l'application ESEO", Snackbar.LENGTH_SHORT).show()
        findViewById<Button>(R.id.button2).setOnClickListener {
            // Votre action
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(ScanActivity.getStartIntent(this))
                finish()
            }, 200)
        }
    }

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }



}