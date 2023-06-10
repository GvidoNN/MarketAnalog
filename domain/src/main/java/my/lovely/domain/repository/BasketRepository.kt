package my.lovely.domain.repository

import my.lovely.domain.database.BasketDao

interface BasketRepository {
    fun getBasketDaoDb(): BasketDao
}