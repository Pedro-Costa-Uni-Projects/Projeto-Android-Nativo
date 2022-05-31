package pt.ulusofona.deisi.cm2122.g21904825_21904341

import android.content.pm.ActivityInfo
import android.location.Geocoder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import pt.ulusofona.deisi.cm2122.g21904825_21904341.databinding.FragmentMapBinding
import pt.ulusofona.deisi.cm2122.g21904825_21904341.maps.FusedLocation
import pt.ulusofona.deisi.cm2122.g21904825_21904341.maps.OnLocationChangedListener
import java.util.*


class MapFragment : Fragment(), OnLocationChangedListener {
    private lateinit var binding: FragmentMapBinding
    private lateinit var geocoder: Geocoder
    private var map: GoogleMap? = null


    //Para não rodar o ecrã
    override fun onResume() {
        super.onResume()
        binding.map.onResume()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.map)
    }

    //Para não rodar o ecrã
    override fun onPause() {
        super.onPause()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.map)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        binding = FragmentMapBinding.bind(view)
        geocoder = Geocoder(context, Locale.getDefault())
        binding.map.onCreate(savedInstanceState)
        binding.map.getMapAsync { map->
            this.map = map
            FusedLocation.registerListener(this)
            placeCamera()
        }
        return binding.root
    }

    override fun onLocationChanged(latitude: Double, longitude: Double) {
        placeCityName(latitude, longitude)
        map?.addMarker(
            MarkerOptions()
                .position(LatLng(latitude, longitude))
        )
    }

    private fun placeCamera() {
        val cameraPosition = CameraPosition.Builder()
            .target(LatLng(39.490961, -7.9271299)) //coordenadas mais ou menos no meio de portugal
            .zoom(6.8f)
            .build()
        map?.animateCamera(
            CameraUpdateFactory.newCameraPosition(cameraPosition)
        )
    }

    private fun placeCityName(latitude: Double, longitude: Double) {
        val addresses = geocoder.getFromLocation(latitude, longitude, 5)
        binding.city.text = "${addresses[0].locality}, ${addresses[0].adminArea}, ${addresses[0].countryName}"
    }

    override fun onDestroy() {
        super.onDestroy()
        FusedLocation.unregisterListener()
    }

    override fun onStart() {
        super.onStart()
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.map)
    }

}