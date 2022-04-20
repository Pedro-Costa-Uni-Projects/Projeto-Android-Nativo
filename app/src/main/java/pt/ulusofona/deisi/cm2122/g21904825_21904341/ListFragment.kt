package pt.ulusofona.deisi.cm2122.g21904825_21904341

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import pt.ulusofona.deisi.cm2122.g21904825_21904341.databinding.FragmentListBinding

class ListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding
    private  var fires = Singleton.getList()
    private val adapter = ListAdapter(::onFireClick)

    override fun onStart() {
        super.onStart()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED //Para poder rodar o ecrã depois de vir do Register
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.list)
        binding.rvHistoricFragment.layoutManager = LinearLayoutManager(activity as Context)
        adapter.updateItems(fires)
        binding.rvHistoricFragment.adapter = adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        binding = FragmentListBinding.bind(view)
        return binding.root
    }


    private fun onFireClick(fire: Fire) {
        NavigationManager.goToDetails(
            parentFragmentManager, fire
        )
    }
}