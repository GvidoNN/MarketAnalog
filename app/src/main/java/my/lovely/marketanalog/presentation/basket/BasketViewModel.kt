package my.lovely.marketanalog.presentation.basket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import my.lovely.domain.model.Basket
import my.lovely.domain.usecase.GetBasketDaoDbUseCase
import my.lovely.domain.usecase.GetDateUseCase
import my.lovely.domain.usecase.GetLocationUseCase
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor(
    private val getBasketDaoDbUseCase: GetBasketDaoDbUseCase,
    private val getLocationUseCase: GetLocationUseCase,
    private val getDateUseCase: GetDateUseCase
) : ViewModel() {

    var dishes = getBasketDaoDbUseCase.getDaoDb().getAllDishes()
    private var dateLiveData = MutableLiveData<String>()
    private var locationLiveData = MutableLiveData<String>()

    val date: LiveData<String>
        get() = dateLiveData

    val location: LiveData<String>
        get() = locationLiveData

    fun updateDish(dish: Basket) = viewModelScope.launch(Dispatchers.IO) {
        getBasketDaoDbUseCase.getDaoDb().updateDish(item = dish)
    }

    fun deleteDish(dish: Basket) = viewModelScope.launch(Dispatchers.IO) {
        getBasketDaoDbUseCase.getDaoDb().deleteDish(item = dish)
    }

    fun getLocation() {
        locationLiveData.value = getLocationUseCase.getLocation()
    }

    fun getDate(){
        dateLiveData.value = getDateUseCase.getDate()
    }

}