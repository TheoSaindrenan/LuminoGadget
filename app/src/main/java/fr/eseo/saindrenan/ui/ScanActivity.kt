package fr.eseo.saindrenan.ui

import android.Manifest

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.eseo.saindrenan.R
import fr.eseo.saindrenan.adapter.ExempleAdapter
import fr.eseo.saindrenan.ui.LocalisationActivity.Companion.PERMISSION_REQUEST_LOCATION

class ScanActivity : AppCompatActivity() {
    lateinit var myRecycler: RecyclerView
    val items = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5").toTypedArray()

    // Le reste de votre code
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        myRecycler.layoutManager = LinearLayoutManager(this)
        myRecycler.adapter = ExempleAdapter(emptyArray()) { item ->
            Toast.makeText(this@ScanActivity, "Connexion à l'item", Toast.LENGTH_SHORT).show()
        }
        findViewById<Button>(R.id.btn_retour3).setOnClickListener {
            // Votre action
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(MainActivity.getStartIntent(this))
                finish()
            }, 200)
        }
    }

    /**
     * Gère l'action après la demande de permission.
     * 2 cas possibles :
     * - Réussite 🎉.
     * - Échec (refus utilisateur).
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val PERMISSION_REQUEST_LOCATION = 9999
        if (requestCode == PERMISSION_REQUEST_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && locationServiceEnabled()) {
                // Permission OK => Nous pouvons lancer l'initialisation du BLE.
                // En appelant la méthode setupBLE()
                // La méthode setupBLE() va initialiser le BluetoothAdapter et lancera le scan.
            } else if (!locationServiceEnabled()) {
                // Inviter à activer la localisation
                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            } else {
                // Permission KO => Gérer le cas.
                // Vous devez ici modifier le code pour gérer le cas d'erreur (permission refusé)
                // Avec par exemple une Dialog
            }
        }
    }
    private fun locationServiceEnabled(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            // This is new method provided in API 28
            val lm = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            lm.isLocationEnabled
        } else {
            // This is Deprecated in API 28
            val mode = Settings.Secure.getInt(this.contentResolver, Settings.Secure.LOCATION_MODE, Settings.Secure.LOCATION_MODE_OFF)
            mode != Settings.Secure.LOCATION_MODE_OFF
        }
    }

    /**
     * Permet de vérifier si l'application possede la permission « Localisation ». OBLIGATOIRE pour scanner en BLE
     */
    private fun hasPermission(): Boolean {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        } else {
            ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED
        }
    }

    /**
     * Demande de la permission (ou des permissions) à l'utilisateur.
     */
    private fun askForPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_REQUEST_LOCATION)
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.BLUETOOTH_CONNECT, Manifest.permission.BLUETOOTH_SCAN), PERMISSION_REQUEST_LOCATION)
        }
    }
    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, ScanActivity::class.java)
        }
    }
}