package pt.ulusofona.deisi.cm2122.g21904825_21904341

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import pt.ulusofona.deisi.cm2122.g21904825_21904341.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding

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

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        binding = FragmentRegisterBinding.bind(view)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.register)

        //Dropdown
        val items = Singleton.getListDistricts()
        val adapter: ArrayAdapter<String>? = this.context?.let { ArrayAdapter<String>(it, android.R.layout.simple_spinner_dropdown_item, items) }
        binding.district.adapter = adapter



    }

}