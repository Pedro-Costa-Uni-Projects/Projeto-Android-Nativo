package pt.ulusofona.deisi.cm2122.g21904825_21904341

import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import pt.ulusofona.deisi.cm2122.g21904825_21904341.databinding.FragmentInfoBinding

class InfoFragment : Fragment() {
    private lateinit var binding: FragmentInfoBinding

    //Para não rodar o ecrã
    override fun onResume() {
        super.onResume()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.info)
    }

    //Para não rodar o ecrã
    override fun onPause() {
        super.onPause()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.info)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_info, container, false)
        binding = FragmentInfoBinding.bind(view)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.info)
        binding.layoutDev1.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:a21904825@alunos.ulht.pt")
            startActivity(intent)
        }
        binding.layoutDev2.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:a21904341@alunos.ulht.pt")
            startActivity(intent)
        }
        binding.ulht.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://www.ulusofona.pt/")
            startActivity(intent)
        }
    }

}