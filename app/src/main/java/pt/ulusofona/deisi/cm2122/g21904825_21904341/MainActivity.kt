package pt.ulusofona.deisi.cm2122.g21904825_21904341

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.os.CountDownTimer
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pt.ulusofona.deisi.cm2122.g21904825_21904341.databinding.ActivityMainBinding

var resourcesStatic : Resources? = null
var contextStatic : Context? = null

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(!screenRotated(savedInstanceState)) {
            NavigationManager.goToDashBoard(supportFragmentManager)
        }

        resourcesStatic = resources
        contextStatic = applicationContext
    }

    override fun onStart() {
        super.onStart()
        setSupportActionBar(binding.toolbar)
        setupDrawerMenu()
        timer.start()
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
    
    //Timer
    private val timer = object : CountDownTimer(100000,20000) {
        var count = 0
        override fun onTick(time: Long) {
            when(count) {
                0 -> {
                    binding.risk.setBackgroundColor(getColor(R.color.reduced))
                    binding.risk.text = getString(R.string.reduced)
                }
                1 -> {
                    binding.risk.setBackgroundColor(getColor(R.color.moderate))
                    binding.risk.text = getString(R.string.moderate)
                }
                2 -> {
                    binding.risk.setBackgroundColor(getColor(R.color.high))
                    binding.risk.text = getString(R.string.high)
                }
                3 -> {
                    binding.risk.setBackgroundColor(getColor(R.color.very_high))
                    binding.risk.text = getString(R.string.very_high)
                }
                4 -> {
                    binding.risk.setBackgroundColor(getColor(R.color.maximum))
                    binding.risk.text = getString(R.string.maximum)
                }
            }
            count++
        }

        override fun onFinish() {
            count = 0
            start()
        }
    }

}