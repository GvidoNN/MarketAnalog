package my.lovely.marketanalog.presentation.basket

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
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import my.lovely.domain.model.Basket
import my.lovely.marketanalog.R
import my.lovely.marketanalog.databinding.FragmentBasketBinding
import my.lovely.marketanalog.presentation.mainCatalog.REQUEST_LOCATION_PERMISSION
import java.util.*


@AndroidEntryPoint
class BasketFragment: Fragment(R.layout.fragment_basket) {

    private val viewModel: BasketViewModel by viewModels()
    private lateinit var adapter: BasketAdapter
    private lateinit var binding: FragmentBasketBinding
    private lateinit var locationManager: LocationManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBasketBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.basketRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = BasketAdapter()
        binding.basketRecyclerView.adapter = adapter

        viewModel.dishes.observe(viewLifecycleOwner) {
            adapter.setBasketList(it)
            adapter.notifyDataSetChanged()
        }

        requestPermission()

        adapter.setOnMinusClickListener(object : BasketAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val dishData = adapter.basketDishesList[position]
                if(dishData.count == 1){
                    deleteDishData(
                        id = dishData.id,
                        name = dishData.name,
                        price = dishData.price,
                        weight = dishData.weight,
                        count = dishData.count,
                        image = dishData.image
                    )
                } else{
                    updateDishCount(
                        id = dishData.id,
                        name = dishData.name,
                        price = dishData.price,
                        weight = dishData.weight,
                        count = dishData.count - 1,
                        image = dishData.image
                    )
                }
            }
        })

        adapter.setOnPlusBookListener(object  : BasketAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                val dishData = adapter.basketDishesList[position]
                updateDishCount(
                    id = dishData.id,
                    name = dishData.name,
                    price = dishData.price,
                    weight = dishData.weight,
                    count = dishData.count + 1,
                    image = dishData.image
                )
            }
        })
    }

    private fun updateDishCount(id: Int, name: String, price: Int, weight: Int, count: Int, image: String) {
        viewModel.updateDish(
            dish = Basket(
                id = id,
                name = name,
                price = price,
                weight = weight,
                count = count,
                image = image
            )
        )
    }

    private fun deleteDishData(id: Int, name: String, price: Int, weight: Int, count: Int, image: String) {
        viewModel.deleteDish(
            dish = Basket(
                id = id,
                name = name,
                price = price,
                weight = weight,
                count = count,
                image = image
            )
        )
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