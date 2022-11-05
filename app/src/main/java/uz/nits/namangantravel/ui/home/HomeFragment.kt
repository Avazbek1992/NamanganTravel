package uz.nits.namangantravel.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.fragment.app.Fragment
import uz.nits.namangantravel.R
import uz.nits.namangantravel.SearchActivity
import uz.nits.namangantravel.databinding.FragmentHomeLayoutBinding
import uz.nits.namangantravel.utils.DataStorage

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeLayoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeLayoutBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val servesNames = resources.getStringArray(R.array.servesNames)
        val imageId = arrayOf(
            R.drawable.ic_sightseeing,
            R.drawable.ic_hotel,
            R.drawable.ic_restaurant,
            R.drawable.ic_airport,
            R.drawable.ic_sport,
            R.drawable.ic_mosque,
            R.drawable.ic_resort_area,
            R.drawable.ic_super_market,
            R.drawable.ic_taxi,
            R.drawable.ic_museum,
            R.drawable.ic_national_park,
            R.drawable.ic_library,
        )
        val gridView: GridView = root.findViewById(R.id.home_grid_view_id)
        gridView.adapter = GridViewAdapter(requireActivity(), imageId)
        gridView.setOnItemClickListener { adapterView, view, i, l ->
            val intent = Intent(requireActivity(), SearchActivity::class.java)
            intent.putExtra(DataStorage.KEY_EXTRA, servesNames[i])
            startActivity(intent)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}