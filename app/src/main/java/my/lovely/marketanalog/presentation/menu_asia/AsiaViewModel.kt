package my.lovely.marketanalog.presentation.menu_asia

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import my.lovely.domain.model.AsiaResponse
import my.lovely.domain.usecase.GetAsiaMenuUseCase
import javax.inject.Inject

@HiltViewModel
class AsiaViewModel @Inject constructor(
    private val getAsiaMenuUseCase: GetAsiaMenuUseCase
) :
    ViewModel() {

    private val asiaMenuLiveData = MutableLiveData<AsiaResponse>()
    private var progressBarLiveData = MutableLiveData<Boolean>()

    val menu: LiveData<AsiaResponse>
        get() = asiaMenuLiveData

    val progressBar: LiveData<Boolean>
        get() = progressBarLiveData

    fun asiaMenuResponse() = viewModelScope.launch(Dispatchers.IO) {
        progressBarLiveData.postValue(true)
        var result = getAsiaMenuUseCase.getAsiaMenu()
        asiaMenuLiveData.postValue(result?.body() ?: null)
        progressBarLiveData.postValue(false)
    }

}