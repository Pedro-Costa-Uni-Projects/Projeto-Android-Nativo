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

        /////////////////////////////////////////////////////////////////////////////////////////////////////////
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