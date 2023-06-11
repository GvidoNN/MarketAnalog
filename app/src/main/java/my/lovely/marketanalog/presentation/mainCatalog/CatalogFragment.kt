package my.lovely.marketanalog.presentation.mainCatalog

import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import my.lovely.marketanalog.R
import my.lovely.marketanalog.databinding.FragmentBasketBinding
import my.lovely.marketanalog.databinding.FragmentCatalogBinding
import java.util.*

const val REQUEST_LOCATION_PERMISSION = 1
@AndroidEntryPoint
class CatalogFragment:  Fragment(R.layout.fragment_catalog) {

    private val catalogViewModel: CatalogViewModel by viewModels()
    private lateinit var adapter: CatalogAdapter
    private lateinit var binding: FragmentCatalogBinding
    private lateinit var locationManager: LocationManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCatalogBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestPermission()

        adapter = CatalogAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        catalogViewModel.catalogResponse()
        catalogViewModel.getDate()

        catalogViewModel.catalog.observe(viewLifecycleOwner){ result ->
            if (result != null) {
                adapter.setCatalogsList(result.catalog)
            }
        }

        catalogViewModel.date.observe(viewLifecycleOwner){
            binding.tvMainDate.text = it
        }

        adapter.setOnItemClickListener(object: CatalogAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                findNavController().navigate(R.id.action_catalogFragment_to_asiaFragment)
            }
        })
    }

    fun requestPermission(){
        locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("MyLog","No perm")
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION), REQUEST_LOCATION_PERMISSION)
            checkCity()
        } else {
            Log.d("MyLog","Yes")
            checkCity()
        }
    }

    fun checkCity(){
        val location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        if (location != null) {
            val geocoder = Geocoder(requireContext(), Locale.getDefault())
            val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            val cityName = addresses!![0].locality
            Log.d("MyLog", "City name: $cityName")
            binding.tvMainCity.text = cityName
        }
    }





}