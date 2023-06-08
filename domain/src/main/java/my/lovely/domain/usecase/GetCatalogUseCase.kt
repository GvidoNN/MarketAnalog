package my.lovely.domain.usecase

import my.lovely.domain.repository.CatalogRepository
import my.lovely.domain.model.DataResponse
import retrofit2.Response
import javax.inject.Inject

class GetCatalogUseCase @Inject constructor(private val catalogRepository: CatalogRepository) {

    suspend fun getCatalog(): Response<DataResponse>? {
        return catalogRepository.getCatalog()
    }


}