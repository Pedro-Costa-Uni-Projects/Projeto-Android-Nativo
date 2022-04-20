package pt.ulusofona.deisi.cm2122.g21904825_21904341

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pt.ulusofona.deisi.cm2122.g21904825_21904341.databinding.FragmentRegisterBinding
import java.util.*

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    private var name = ""
    private var cc = 0
    private var district = ""
    private var timestamp = 0L
    private var photo : Bitmap? = null

    //Para não rodar o ecrã
    override fun onResume() {
        super.onResume()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    //Para não rodar o ecrã
    override fun onPause() {
        super.onPause()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_NOSENSOR
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        binding = FragmentRegisterBinding.bind(view)

        //Get Foto
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result ->
            if (result.resultCode == Activity.RESULT_OK) {
                handleCameraImage(result.data)
            }
        }

        binding.photo.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            resultLauncher.launch(cameraIntent)
        }
        //Get Foto

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.register)

        //Data pré preenchida
        timestamp = Date().time
        binding.date.text = getData(timestamp)
        //Data pré preenchida

        //Dropdown
        val items = Singleton.getListDistricts()
        val adapter: ArrayAdapter<String>? = this.context?.let { ArrayAdapter<String>(it, android.R.layout.simple_spinner_dropdown_item, items) }
        binding.district.adapter = adapter
        //Dropdown

        //Submeter
        binding.buttonSubmit.setOnClickListener {
            //name
            name = binding.name.editableText.toString()
            if(name == "") {
                binding.name.error = getString(R.string.error_empty_fill)
            }

            //cc
            if(binding.cc.editableText.toString() == "" ) {
                binding.cc.error = getString(R.string.error_empty_fill)
                cc = 0
            } else if (binding.cc.editableText.toString().length < 8){
                binding.cc.error = getString(R.string.error_cc_fill)
                cc = 0
            } else if (binding.cc.editableText.toString() != "" ){
                cc = binding.cc.editableText.toString().toInt()
            }

            //district
            district = binding.district.selectedItem.toString()
            if (district == getString(R.string.district_hint_form)) {
                district = ""
                val errorText : TextView = binding.district.selectedView as TextView
                errorText.error = getString(R.string.error_empty_fill)
            }

            //Submit
            if (name != "" && cc != 0 && district != "") {
                val fire = Fire(name, cc, district, timestamp, photo)

                CoroutineScope(Dispatchers.IO).launch {
                    Singleton.add(fire)
                }

                //Ir para Lista
                NavigationManager.goToList(parentFragmentManager)
            }

        }

    }

    private fun handleCameraImage(intent: Intent?) {
        photo = intent?.extras?.get("data") as Bitmap
        binding.photo.setImageBitmap(photo)
    }

}