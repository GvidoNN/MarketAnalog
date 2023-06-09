package my.lovely.marketanalog.presentation.menu_asia

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import my.lovely.marketanalog.R
import my.lovely.marketanalog.presentation.main.CatalogAdapter
import my.lovely.marketanalog.presentation.main.CatalogViewModel

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
    }

}