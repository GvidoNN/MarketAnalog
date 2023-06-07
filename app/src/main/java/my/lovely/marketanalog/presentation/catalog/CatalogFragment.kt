package my.lovely.marketanalog.presentation.catalog

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import my.lovely.marketanalog.R


@AndroidEntryPoint
class CatalogFragment:  Fragment(R.layout.fragment_catalog) {

    private val catalogViewModel: CatalogViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        catalogViewModel.catalogResponse()

        catalogViewModel.catalog.observe(viewLifecycleOwner){ result ->
            if (result != null) {
                Log.d("MyLog",result.toString())
            }
        }
    }




}