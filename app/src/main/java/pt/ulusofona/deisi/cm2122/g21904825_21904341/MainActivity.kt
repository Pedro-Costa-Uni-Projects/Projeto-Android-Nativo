package pt.ulusofona.deisi.cm2122.g21904825_21904341

import android.Manifest
import android.content.Context
import android.content.res.Resources
import android.location.Geocoder
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.fondesa.kpermissions.allGranted
import com.fondesa.kpermissions.extension.send
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pt.ulusofona.deisi.cm2122.g21904825_21904341.databinding.ActivityMainBinding
import pt.ulusofona.deisi.cm2122.g21904825_21904341.maps.FusedLocation
import pt.ulusofona.deisi.cm2122.g21904825_21904341.maps.OnLocationChangedListener
import java.util.*

var resourcesStatic : Resources? = null
var contextStatic : Context? = null

class MainActivity : AppCompatActivity(), OnLocationChangedListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var geocoder: Geocoder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Perguntar por acesso a gps
        permissionsBuilder(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION).build().send { result ->
            if (result.allGranted()) {
                //Iniciar a busca da localização
                FusedLocation.start(this)
                if(!screenRotated(savedInstanceState)) {
                    NavigationManager.goToDashBoard(supportFragmentManager)
                }
            } else {
                finish()
            }
        }

        resourcesStatic = resources
        contextStatic = applicationContext

        //Se tem ligação a internet limpa a bd
        // deixando apenas os fogos inseridos manualmente,
        // depois vai buscar a api os fogos mais recentes
        if (ConnectivityUtil.isOnline(this.applicationContext)) {
            CoroutineScope(Dispatchers.IO).launch {
                Singleton.dao?.purgeDB(true)
                Singleton.getFromAPIaddToLocalDB()
            }
        }

    }

    override fun onStart() {
        super.onStart()
        setSupportActionBar(binding.toolbar)
        setupDrawerMenu()
        geocoder = Geocoder(this.applicationContext, Locale.getDefault())
        FusedLocation.registerListener(this)

    }

    private fun screenRotated(savedInstanceState: Bundle?) : Boolean {
        return savedInstanceState != null
    }

    private fun setupDrawerMenu() {
        val toggle = ActionBarDrawerToggle(this,
            binding.drawer, binding.toolbar,
            R.string.drawer_open, R.string.drawer_close
        )
        binding.navDrawer.setNavigationItemSelectedListener {
            onClickNavigationItem(it)
        }
        binding.drawer.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun onClickNavigationItem(item: MenuItem) : Boolean {
        when(item.itemId) {
            R.id.nav_dashboard -> {
                NavigationManager.goToDashBoard(
                    supportFragmentManager
                )
            }
            R.id.nav_map -> {
                NavigationManager.goToMap(
                    supportFragmentManager
                )
            }
            R.id.nav_list -> {
                NavigationManager.goToList(
                    supportFragmentManager
                )
            }
            R.id.nav_register -> {
                NavigationManager.goToRegister(
                    supportFragmentManager
                )
            }
            R.id.nav_info-> {
                NavigationManager.goToInfo(
                    supportFragmentManager
                )
            }
        }
        binding.drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if(binding.drawer.isDrawerOpen(GravityCompat.START)) {
            binding.drawer.closeDrawer(GravityCompat.START)
        } else if(supportFragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            super.onBackPressed()
        }
    }

    //Risco
    override fun onLocationChanged(latitude: Double, longitude: Double) {
        val addresses = geocoder.getFromLocation(latitude, longitude, 5)

        Singleton.setDistrict(addresses[0].adminArea)
        Singleton.setCounty(addresses[0].locality)

        Singleton.getRisk {

            //Dedicado ao Bernardo
            if (Singleton.risco != "") {
                when(Singleton.risco.split("\r")[1].split(" - ")[1].split(",")[0]) {
                    "Reduzido" -> {
                        binding.risk.setBackgroundColor(getColor(R.color.reduced))
                        binding.risk.text = getString(R.string.reduced)
                    }
                    "Moderado" -> {
                        binding.risk.setBackgroundColor(getColor(R.color.moderate))
                        binding.risk.text = getString(R.string.moderate)
                    }
                    "Elevado" -> {
                        binding.risk.setBackgroundColor(getColor(R.color.high))
                        binding.risk.text = getString(R.string.high)
                    }
                    "Muito Elevado" -> {
                        binding.risk.setBackgroundColor(getColor(R.color.very_high))
                        binding.risk.text = getString(R.string.very_high)
                    }
                    "Máximo" -> {
                        binding.risk.setBackgroundColor(getColor(R.color.maximum))
                        binding.risk.text = getString(R.string.maximum)
                    }

                }
            }

        }
    }

}