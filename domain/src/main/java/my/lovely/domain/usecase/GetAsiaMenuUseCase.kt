package my.lovely.domain.usecase

import my.lovely.domain.model.AsiaResponse
import my.lovely.domain.repository.AsiaMenuRepository
import retrofit2.Response
import javax.inject.Inject

class GetAsiaMenuUseCase @Inject constructor(private val asiaMenuRepository: AsiaMenuRepository) {

    suspend fun getAsiaMenu(): Response<AsiaResponse>? {
        return asiaMenuRepository.getAsianMenu()
    }
}