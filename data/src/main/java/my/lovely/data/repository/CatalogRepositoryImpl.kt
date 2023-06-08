package my.lovely.data.repository

import my.lovely.data.api.DataService
import my.lovely.domain.repository.CatalogRepository
import my.lovely.domain.model.DataResponse
import retrofit2.Response
import javax.inject.Inject

class CatalogRepositoryImpl @Inject constructor(private val dataService: DataService):
    CatalogRepository {

    override suspend fun getCatalog(): Response<DataResponse>?{
        return try{
            val result = dataService.getCatalog()
            result
        } catch (e: java.net.UnknownHostException){
            null
        }
    }
}