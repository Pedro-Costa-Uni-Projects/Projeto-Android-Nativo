package pt.ulusofona.deisi.cm2122.g21904825_21904341

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import pt.ulusofona.deisi.cm2122.g21904825_21904341.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Put tile here to change in first opening
        title = getString(R.string.home)

        if(!screenRotated(savedInstanceState)) {
            NavigationManager.goToDashBoard(supportFragmentManager)
        }
    }

    override fun onStart() {
        super.onStart()
        setSupportActionBar(binding.toolbar)
        setupDrawerMenu()

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

}