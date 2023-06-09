package my.lovely.marketanalog.presentation.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import my.lovely.marketanalog.R

@AndroidEntryPoint
class CatalogFragment:  Fragment(R.layout.fragment_catalog) {

    private val catalogViewModel: CatalogViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CatalogAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = requireView().findViewById(R.id.recyclerView)

        adapter = CatalogAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        catalogViewModel.catalogResponse()

        catalogViewModel.catalog.observe(viewLifecycleOwner){ result ->
            if (result != null) {
                adapter.setCatalogsList(result.catalog)
            }
        }

        adapter.setOnItemClickListener(object: CatalogAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                findNavController().navigate(R.id.action_catalogFragment_to_asiaFragment)
            }
        })
    }




}