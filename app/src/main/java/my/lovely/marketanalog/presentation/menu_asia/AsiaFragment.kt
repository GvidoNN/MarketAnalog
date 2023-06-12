package my.lovely.marketanalog.presentation.menu_asia

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import my.lovely.domain.model.Basket
import my.lovely.domain.model.Dishe
import my.lovely.domain.model.Tag
import my.lovely.marketanalog.R
import my.lovely.marketanalog.databinding.FragmentAsiaMenuBinding

@AndroidEntryPoint
class AsiaFragment : Fragment(R.layout.fragment_asia_menu) {

    private val asiaViewModel: AsiaViewModel by viewModels()
    private lateinit var binding: FragmentAsiaMenuBinding
    private lateinit var adapter: AsiaAdapter
    private lateinit var tagAdapter: TagAdapter
    private lateinit var errorContainer: LinearLayout
    private lateinit var btErrorTryAgain: Button
    private var dishesList = arrayListOf<Dishe>()
    private var list = arrayListOf<Tag>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAsiaMenuBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        errorContainer = requireView().findViewById(R.id.errorContainer)
        btErrorTryAgain = requireView().findViewById(R.id.btErrorTryAgain)

        val fragmentTitle = arguments?.getString("Category")
        binding.tvMenuCategory.text = fragmentTitle.toString()

        binding.imBtBack.setOnClickListener {
            findNavController().navigate(R.id.action_asiaFragment_to_catalogFragment)
        }

        asiaViewModel.progressBar.observe(viewLifecycleOwner) {
            if (it == true) {
                binding.progressBarMenu.isVisible = true
                errorContainer.isVisible = false
                binding.recyclerView.isVisible = false
            } else {
                binding.progressBarMenu.isVisible = false
            }
        }

        adapter = AsiaAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(context, 3)

        tagAdapter = TagAdapter()
        binding.recyclerViewTags.adapter = tagAdapter
        binding.recyclerViewTags.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        asiaViewModel.asiaMenuResponse()

        asiaViewModel.menu.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                dishesList = result.dishes as ArrayList<Dishe>
                adapter.setAsiaList(result.dishes)
                binding.recyclerView.isVisible = true

                result.dishes.flatMap { it.tegs }.forEach { list.add(Tag(tag = it)) }
                tagAdapter.setTagList(list.distinct())

            } else {
                errorContainer.isVisible = true
                binding.recyclerView.isVisible = false
            }
        }

        asiaViewModel.sortedMenu.observe(viewLifecycleOwner) {
            Log.d("MyLog", "Сортировка")
            adapter.setAsiaList(it)
        }

//        binding.btDone.setOnClickListener {
//            asiaViewModel.sortDishes(dishes = dishesList, tag = "Салаты")
//        }

        adapter.setOnItemClickListener(object : AsiaAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                showInfoAboutDish(position = position)
            }
        })

        tagAdapter.setOnItemClickListener(object : TagAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                Log.d("MyLog",tagAdapter.tagsList[position].tag)
                asiaViewModel.sortDishes(dishes = dishesList, tag = tagAdapter.tagsList[position].tag)
            }
        })


    }

    private fun showInfoAboutDish(position: Int) {
        val dialog = Dialog(requireContext())
        val dishData = adapter.asianList[position]

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.alert_dialog_dish_card)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btAlertCross: ImageButton = dialog.findViewById(R.id.imBtCross)
        val btAlertAddToCart: Button = dialog.findViewById(R.id.btCardAddToBucket)
        val imAlertDish: ImageView = dialog.findViewById(R.id.imCardDish)
        val tvAlertDishName: TextView = dialog.findViewById(R.id.tvCardName)
        val tvAlertDishPrice: TextView = dialog.findViewById(R.id.tvCardPrice)
        val tvAlertDishWeight: TextView = dialog.findViewById(R.id.tvCardWeight)
        val tvAlertDishDesc: TextView = dialog.findViewById(R.id.tvCardDescription)

        Glide.with(requireContext()).load(dishData.image_url).into(imAlertDish)
        tvAlertDishName.text = dishData.name
        tvAlertDishPrice.text = dishData.price.toString() + " ₽"
        tvAlertDishWeight.text = " · " + dishData.weight.toString() + "г"
        tvAlertDishDesc.text = dishData.description

        btAlertCross.setOnClickListener {
            dialog.dismiss()
        }

        btAlertAddToCart.setOnClickListener {
            addToBasketDish(position = position)
        }

        dialog.show()
    }

    private fun addToBasketDish(position: Int) {
        val dishData = adapter.asianList[position]
        val dish = Basket(
            id = 0,
            name = dishData.name,
            price = dishData.price,
            weight = dishData.weight,
            count = 1,
            image = dishData.image_url
        )
        asiaViewModel.insertDish(dish = dish)
    }

}