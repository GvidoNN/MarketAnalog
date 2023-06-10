package my.lovely.domain.usecase

import my.lovely.domain.database.BasketDao
import my.lovely.domain.repository.BasketRepository
import javax.inject.Inject

class GetBasketDaoDbUseCase @Inject constructor(private val basketRepository: BasketRepository) {

    fun getDaoDb(): BasketDao {
        return basketRepository.getBasketDaoDb()
    }
}