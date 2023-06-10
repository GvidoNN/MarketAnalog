package my.lovely.marketanalog.presentation.basket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import my.lovely.domain.model.Basket
import my.lovely.domain.usecase.GetBasketDaoDbUseCase
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor(private val getBasketDaoDbUseCase: GetBasketDaoDbUseCase): ViewModel() {

    var dishes = getBasketDaoDbUseCase.getDaoDb().getAllDishes()
    fun updateDish(dish: Basket) = viewModelScope.launch(Dispatchers.IO) {
        getBasketDaoDbUseCase.getDaoDb().updateDish(item = dish)
    }

    fun deleteDish(dish: Basket) = viewModelScope.launch(Dispatchers.IO) {
        getBasketDaoDbUseCase.getDaoDb().deleteDish(item = dish)
    }

}