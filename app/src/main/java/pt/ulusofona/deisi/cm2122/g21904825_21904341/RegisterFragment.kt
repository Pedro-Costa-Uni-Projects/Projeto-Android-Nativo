package pt.ulusofona.deisi.cm2122.g21904825_21904341

import android.Manifest
import android.app.Activity
import android.app.SearchableInfo
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.fondesa.kpermissions.allGranted
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.fondesa.kpermissions.extension.send
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pt.ulusofona.deisi.cm2122.g21904825_21904341.databinding.FragmentRegisterBinding
import java.io.ByteArrayOutputStream
import java.util.*
import org.apache.commons.codec.binary.Base64

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    private var name = ""
    private var cc = 0
    private var district = ""
    private var timestamp = 0L
    private var photo : String? = null

    //Para não rodar o ecrã
    override fun onResume() {
        super.onResume()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.register)
    }

    //Para não rodar o ecrã
    override fun onPause() {
        super.onPause()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR

        //Força esta orientação para resolver bug da foto desaparecer
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.register)
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        binding = FragmentRegisterBinding.bind(view)

        //Tem que ser aqui se não da erro
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result ->
                    if (result.resultCode == Activity.RESULT_OK) {
                        val bitmap = result.data?.extras?.get("data") as Bitmap
                        val baos = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
                        CoroutineScope(Dispatchers.Main).launch {
                            val photoByteArray = baos.toByteArray()
                            photo = Base64.encodeBase64(photoByteArray).toString(Charsets.UTF_8)
                            binding.photo.setImageBitmap(BitmapFactory.decodeByteArray(photoByteArray, 0, photoByteArray.size))
                        }
                    }
        }

        binding.photo.setOnClickListener {
            takePhoto()
        }

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
                val fire = FireRoom(false, name, cc, district, "NaN", "NaN", 0, 0, 0, "NaN", timestamp, "NaN", photo)

                CoroutineScope(Dispatchers.IO).launch {
                    Singleton.dao?.insert(fire)
                }

                //Mensagem de sucesso
                Toast.makeText(this.context, getString(R.string.submitted), Toast.LENGTH_SHORT).show()

                //Ir para Lista
                NavigationManager.goToList(parentFragmentManager)
            }

        }

    }

    private fun takePhoto() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        permissionsBuilder(Manifest.permission.CAMERA).build().send { result->
            if (result.allGranted()) {
                resultLauncher.launch(cameraIntent)
            } else {
                Toast.makeText(context, getString(R.string.no_camera), Toast.LENGTH_LONG).show()
            }
        }
    }
}