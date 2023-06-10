package my.lovely.marketanalog.presentation.menu_asia

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
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
                showInfoAboutDish()
            }
        })
    }

    private fun showInfoAboutDish(){
        val dialog = Dialog(requireContext())

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.alert_dialog_dish_card)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.show()
    }

}