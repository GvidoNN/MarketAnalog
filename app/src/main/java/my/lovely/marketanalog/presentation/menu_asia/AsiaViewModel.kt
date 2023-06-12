package my.lovely.marketanalog.presentation.menu_asia

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import my.lovely.domain.model.AsiaResponse
import my.lovely.domain.model.Basket
import my.lovely.domain.model.Dishe
import my.lovely.domain.usecase.GetAsiaMenuUseCase
import my.lovely.domain.usecase.GetBasketDaoDbUseCase
import my.lovely.domain.usecase.SortByTagUseCase
import javax.inject.Inject

@HiltViewModel
class AsiaViewModel @Inject constructor(
    private val getAsiaMenuUseCase: GetAsiaMenuUseCase,
    private val getBasketDaoDbUseCase: GetBasketDaoDbUseCase,
    private val sortByTagUseCase: SortByTagUseCase
) :
    ViewModel() {

    private val asiaMenuLiveData = MutableLiveData<AsiaResponse>()
    private var progressBarLiveData = MutableLiveData<Boolean>()
    private val asiaMenuSortedLiveData = MutableLiveData<ArrayList<Dishe>>()

    val menu: LiveData<AsiaResponse>
        get() = asiaMenuLiveData

    val progressBar: LiveData<Boolean>
        get() = progressBarLiveData

    val sortedMenu: LiveData<ArrayList<Dishe>>
        get() = asiaMenuSortedLiveData

    fun asiaMenuResponse() = viewModelScope.launch(Dispatchers.IO) {
        progressBarLiveData.postValue(true)
        val result = getAsiaMenuUseCase.getAsiaMenu()
        asiaMenuLiveData.postValue(result?.body())
        progressBarLiveData.postValue(false)
    }

    fun insertDish(dish: Basket) = viewModelScope.launch(Dispatchers.IO) {
        getBasketDaoDbUseCase.getDaoDb().insertDish(item = dish)
    }

    fun sortDishes(dishes: ArrayList<Dishe>, tag:String) {
        asiaMenuSortedLiveData.value = sortByTagUseCase.sort(dishesList = dishes, tag = tag)
    }

}