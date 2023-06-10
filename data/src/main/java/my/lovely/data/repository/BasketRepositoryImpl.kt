package my.lovely.data.repository

import my.lovely.domain.database.BasketDao
import my.lovely.domain.repository.BasketRepository
import javax.inject.Inject

class BasketRepositoryImpl @Inject constructor(private val basketDao: BasketDao): BasketRepository {

    override fun getBasketDaoDb(): BasketDao{
        return basketDao
    }

}