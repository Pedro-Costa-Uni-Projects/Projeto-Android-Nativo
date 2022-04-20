package pt.ulusofona.deisi.cm2122.g21904825_21904341

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import pt.ulusofona.deisi.cm2122.g21904825_21904341.databinding.FragmentDetailsBinding

private const val ARG_FIRE = "fire"

class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private var fire: Fire? = null

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            fire = it.getParcelable(ARG_FIRE)
        }
    }

    override fun onStart() {
        super.onStart()
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.details)

        //Localização
        binding.localization.text = getString(R.string.localization_list_details, fire?.getDistrict(), fire?.getCounty(), fire?.getParish())

        //Estado e Data
        binding.data.text = fire?.getTimestamp()?.let { getData(it) }
        binding.state.text = fire?.getState()

        //Operacionais
        binding.planes.text = fire?.getAerial()
        binding.cars.text = fire?.getVehicles()
        binding.peoples.text = fire?.getOperational()

        //Fotografia
        if(fire?.getPhoto() != null) {
            binding.photo.setImageBitmap(fire?.getPhoto())
        }

        //Observações
        binding.observations.text = fire?.getComments()

        //Nome e CC
        binding.name.text = getString(R.string.name_details,  fire?.getName())
        binding.cc.text = getString(R.string.cc_details, fire?.getCC())

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_details, container, false)
        binding = FragmentDetailsBinding.bind(view)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(fire: Fire) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_FIRE, fire)
                }
            }
    }

}