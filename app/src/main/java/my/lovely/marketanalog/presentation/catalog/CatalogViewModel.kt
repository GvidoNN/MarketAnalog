package my.lovely.marketanalog.presentation.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import my.lovely.domain.model.DataResponse
import my.lovely.domain.usecase.GetCatalogUseCase
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(private val getCatalogUseCase: GetCatalogUseCase): ViewModel() {

    private val catalogLiveData = MutableLiveData<DataResponse>()
    private var progressBarLiveData = MutableLiveData<Boolean>()

    val catalog: LiveData<DataResponse>
        get() = catalogLiveData

    val progressBar: LiveData<Boolean>
        get() = progressBarLiveData

    fun catalogResponse() = viewModelScope.launch(Dispatchers.IO) {
        progressBarLiveData.postValue(true)
        var result = getCatalogUseCase.getCatalog()
        catalogLiveData.postValue(result?.body() ?: null)
        progressBarLiveData.postValue(false)
    }
    
}

