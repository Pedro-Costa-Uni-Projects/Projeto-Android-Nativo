package pt.ulusofona.deisi.cm2122.g21904825_21904341

import android.content.pm.ActivityInfo
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import pt.ulusofona.deisi.cm2122.g21904825_21904341.databinding.FragmentDashboardBinding
import pt.ulusofona.deisi.cm2122.g21904825_21904341.maps.FusedLocation
import pt.ulusofona.deisi.cm2122.g21904825_21904341.maps.OnLocationChangedListener
import java.io.IOException
import java.lang.NullPointerException
import java.util.*

class DashboardFragment : Fragment(), OnLocationChangedListener {
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var geocoder: Geocoder

    private val TAG = DashboardFragment::class.java.simpleName

    //Para não rodar o ecrã
    override fun onResume() {
        super.onResume()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.home)
    }

    //Para não rodar o ecrã
    override fun onPause() {
        super.onPause()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.home)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        binding = FragmentDashboardBinding.bind(view)
        geocoder = Geocoder(context, Locale.getDefault())
        FusedLocation.registerListener(this)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.home)
        Singleton.getList {  }

        binding.fab.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(R.id.frame, DashboardFragment())?.addToBackStack(null)?.commit()
        }

    }

    override fun onLocationChanged(latitude: Double, longitude: Double) {
        try {
            val addresses = geocoder.getFromLocation(latitude, longitude, 5)

            try {
                Singleton.setDistrict(addresses[0].adminArea)
                Singleton.setCounty(addresses[0].locality)
            } catch (ex2: NullPointerException) {
                Log.e(TAG, ex2.toString())
            }

        } catch (ex: IOException) {
            Log.e(TAG, ex.toString())
        }

        Singleton.activeDistrictAndCounty("d") {
            binding.fireActiveDistrictNumber.text = it.toString()
        }

        Singleton.activeDistrictAndCounty("m") {
            binding.fireActiveMunicipalityNumber.text = it.toString()
        }

        Singleton.activeFires{
            binding.fireActiveNumber.text = it.toString()
        }

        Singleton.getDistrict {
            binding.fireActiveDistrict.text = getString(R.string.fire_active_district, it)
        }

        Singleton.getCounty {
            binding.fireActiveMunicipality.text = getString(R.string.fire_active_municipality, it)
        }

    }

}