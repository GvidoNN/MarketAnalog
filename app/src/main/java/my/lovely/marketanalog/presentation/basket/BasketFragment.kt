package my.lovely.marketanalog.presentation.basket

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import my.lovely.domain.model.Basket
import my.lovely.marketanalog.R
import my.lovely.marketanalog.databinding.FragmentBasketBinding


@AndroidEntryPoint
class BasketFragment: Fragment(R.layout.fragment_basket) {

    private val viewModel: BasketViewModel by viewModels()
    private lateinit var adapter: BasketAdapter
    private lateinit var binding: FragmentBasketBinding

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

        adapter.setOnMinusClickListener(object : BasketAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                Log.d("MyLog","minus")
            }
        })

        adapter.setOnPlusBookListener(object  : BasketAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                Log.d("MyLog","plus")
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



}