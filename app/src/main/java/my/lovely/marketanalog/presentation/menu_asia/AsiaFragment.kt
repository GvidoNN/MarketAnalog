package my.lovely.marketanalog.presentation.menu_asia

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import my.lovely.domain.model.Basket
import my.lovely.marketanalog.R
import my.lovely.marketanalog.presentation.mainCatalog.CatalogAdapter

@AndroidEntryPoint
class AsiaFragment: Fragment(R.layout.fragment_asia_menu) {

    private val asiaViewModel: AsiaViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AsiaAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = requireView().findViewById(R.id.recyclerView)

        adapter = AsiaAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(context, 3)

        asiaViewModel.asiaMenuResponse()

        asiaViewModel.menu.observe(viewLifecycleOwner){ result ->
            if (result != null) {
                result.dishes.forEach {
                    Log.d("MyLog",it.toString())
                }
                adapter.setAsiaList(result.dishes)
            }
        }

        adapter.setOnItemClickListener(object: AsiaAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                showInfoAboutDish(position = position)
            }
        })
    }

    private fun showInfoAboutDish(position: Int){
        val dialog = Dialog(requireContext())
        val dishData = adapter.asianList[position]

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.alert_dialog_dish_card)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btAlertCross: ImageButton = dialog.findViewById(R.id.imBtCross)
        val btAlertLike: ImageButton = dialog.findViewById(R.id.imBtLike)
        val btAlertAddToCart: Button = dialog.findViewById(R.id.btCardAddToBucket)
        val imAlertDish: ImageView = dialog.findViewById(R.id.imCardDish)
        val tvAlertDishName: TextView = dialog.findViewById(R.id.tvCardName)
        val tvAlertDishPrice: TextView = dialog.findViewById(R.id.tvCardPrice)
        val tvAlertDishWeight: TextView = dialog.findViewById(R.id.tvCardWeight)

        Glide.with(requireContext()).load(dishData.image_url).into(imAlertDish)
        tvAlertDishName.text = dishData.name
        tvAlertDishPrice.text = dishData.price.toString()
        tvAlertDishWeight.text = dishData.weight.toString()


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
        val dish = Basket(id = 0, name = dishData.name, price = dishData.price, weight = dishData.weight, count = 1, image = dishData.image_url)
        asiaViewModel.insertDish(dish = dish)
    }

}