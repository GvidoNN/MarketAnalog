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
        Log.d("MyLog","OnViewCreated")



        binding.basketRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = BasketAdapter()
        binding.basketRecyclerView.adapter = adapter
        var money = 0

        viewModel.dishes.observe(viewLifecycleOwner) { result ->
            money = 0
            adapter.setBasketList(result)
            result.forEach {
                money += it.price * it.count
            }
            binding.btBasketPay.text = getString(R.string.pay) + money + " ₽"
            adapter.notifyDataSetChanged()
        }

        viewModel.date.observe(viewLifecycleOwner) {
            binding.tvMainDate.text = it
        }

        viewModel.location.observe(viewLifecycleOwner) {
            binding.tvMainCity.text = it
        }

        requestPermission()
        viewModel.getDate()

        adapter.setOnMinusClickListener(object : BasketAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val dishData = adapter.basketDishesList[position]
                if (dishData.count == 1) {
                    viewModel.deleteDish(
                        dish = Basket(
                        id = dishData.id,
                        name = dishData.name,
                        price = dishData.price,
                        weight = dishData.weight,
                        count = dishData.count,
                        image = dishData.image
                    ))
                } else {
                    viewModel.updateDish(
                        dish = Basket(
                            id = dishData.id,
                            name = dishData.name,
                            price = dishData.price,
                            weight = dishData.weight,
                            count = dishData.count - 1,
                            image = dishData.image
                        )
                    )
                }
            }
        })

        adapter.setOnPlusBookListener(object : BasketAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val dishData = adapter.basketDishesList[position]
                viewModel.updateDish(
                    dish = Basket(
                        id = dishData.id,
                        name = dishData.name,
                        price = dishData.price,
                        weight = dishData.weight,
                        count = dishData.count + 1,
                        image = dishData.image
                    )
                )
            }
        })
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