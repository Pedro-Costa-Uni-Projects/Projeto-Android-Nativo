package pt.ulusofona.deisi.cm2122.g21904825_21904341

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import pt.ulusofona.deisi.cm2122.g21904825_21904341.databinding.FragmentMapBinding
import pt.ulusofona.deisi.cm2122.g21904825_21904341.maps.FusedLocation


class MapFragment : Fragment() {
    private lateinit var binding: FragmentMapBinding
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
        binding.map.onCreate(savedInstanceState)
        binding.map.getMapAsync { map->
            this.map = map
            placeCamera()
            for (fire in Singleton.getFires()) {
                map.addMarker(
                    MarkerOptions()
                        .position(LatLng(fire.getLatitude(), fire.getLongitude()))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.fire))

                )
            }
        }
        return binding.root
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

    override fun onDestroy() {
        super.onDestroy()
        FusedLocation.unregisterListener()
    }

    override fun onStart() {
        super.onStart()
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.map)
    }

}