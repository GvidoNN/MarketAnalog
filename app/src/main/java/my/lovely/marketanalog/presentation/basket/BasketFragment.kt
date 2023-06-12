package my.lovely.marketanalog.presentation.basket

import android.content.pm.PackageManager
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


@AndroidEntryPoint
class BasketFragment : Fragment(R.layout.fragment_basket) {

    private val viewModel: BasketViewModel by viewModels()
    private lateinit var adapter: BasketAdapter
    private lateinit var binding: FragmentBasketBinding
    private var money = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBasketBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var first = true

        binding.basketRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = BasketAdapter()
        binding.basketRecyclerView.adapter = adapter

        viewModel.dishes.observe(viewLifecycleOwner) { result ->
            if (first) {
                result.forEach {
                    money += it.price
                }
                first = !first
            }
            binding.btBasketPay.text = getString(R.string.pay) + money + " ₽"
            adapter.setBasketList(result)
            adapter.notifyDataSetChanged()
        }

        requestPermission()
        viewModel.getDate()

        viewModel.date.observe(viewLifecycleOwner){
            binding.tvMainDate.text = it
        }

        viewModel.location.observe(viewLifecycleOwner){
            binding.tvMainCity.text = it
        }

        adapter.setOnMinusClickListener(object : BasketAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val dishData = adapter.basketDishesList[position]
                if (dishData.count == 1) {
                    deleteDishData(
                        id = dishData.id,
                        name = dishData.name,
                        price = dishData.price,
                        weight = dishData.weight,
                        count = dishData.count,
                        image = dishData.image
                    )
                } else {
                    updateDishCount(
                        id = dishData.id,
                        name = dishData.name,
                        price = dishData.price,
                        weight = dishData.weight,
                        count = dishData.count - 1,
                        image = dishData.image
                    )
                }
                money -= dishData.price
            }
        })

        adapter.setOnPlusBookListener(object : BasketAdapter.OnItemClickListener {
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
                money += dishData.price
            }
        })
    }

    private fun updateDishCount(
        id: Int,
        name: String,
        price: Int,
        weight: Int,
        count: Int,
        image: String
    ) {
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

    private fun deleteDishData(
        id: Int,
        name: String,
        price: Int,
        weight: Int,
        count: Int,
        image: String
    ) {
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

    fun requestPermission() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                REQUEST_LOCATION_PERMISSION
            )
            viewModel.getLocation()
        } else {
            viewModel.getLocation()
        }
    }

}