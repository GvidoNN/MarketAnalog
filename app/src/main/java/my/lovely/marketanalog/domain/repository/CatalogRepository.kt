package my.lovely.marketanalog.domain.repository

import my.lovely.marketanalog.domain.model.DataResponse
import retrofit2.Response

interface CatalogRepository {

    suspend fun getCatalog(): Response<DataResponse>?

}