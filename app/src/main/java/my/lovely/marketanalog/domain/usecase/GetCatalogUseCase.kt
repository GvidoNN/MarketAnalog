package my.lovely.marketanalog.domain.usecase

import android.util.Log
import my.lovely.marketanalog.domain.repository.CatalogRepository
import my.lovely.marketanalog.domain.model.DataResponse
import retrofit2.Response
import javax.inject.Inject

class GetCatalogUseCase @Inject constructor(private val catalogRepository: CatalogRepository) {

    suspend fun getCatalog(): Response<DataResponse>? {
        return catalogRepository.getCatalog()
    }


}