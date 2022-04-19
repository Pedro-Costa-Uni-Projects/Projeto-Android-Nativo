package pt.ulusofona.deisi.cm2122.g21904825_21904341

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import pt.ulusofona.deisi.cm2122.g21904825_21904341.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding

    //Para não rodar o ecrã
    override fun onResume() {
        super.onResume()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    //Para não rodar o ecrã
    override fun onPause() {
        super.onPause()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        binding = FragmentDashboardBinding.bind(view)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.home)

        val activePortugal = Singleton.activeFires()
        binding.fireActiveNumber.text = activePortugal.toString()

        val district = Singleton.getDistrict()
        binding.fireActiveDistrict.text = getString(R.string.fire_active_district, district)

        val activeDistrict = Singleton.activeDistrictandCounty("d")
        binding.fireActiveDistrictNumber.text = activeDistrict.toString()

        val municipality = Singleton.getCounty()
        binding.fireActiveMunicipality.text = getString(R.string.fire_active_municipality, municipality)

        val activeMunicipality = Singleton.activeDistrictandCounty("m")
        binding.fireActiveMunicipalityNumber.text = activeMunicipality.toString()

    }
}