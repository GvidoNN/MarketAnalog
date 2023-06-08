package my.lovely.domain.repository

import my.lovely.domain.model.DataResponse
import retrofit2.Response

interface CatalogRepository {

    suspend fun getCatalog(): Response<DataResponse>?

}