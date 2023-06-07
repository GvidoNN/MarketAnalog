package my.lovely.marketanalog.data.repository

import my.lovely.marketanalog.data.api.DataService
import my.lovely.marketanalog.domain.repository.CatalogRepository
import my.lovely.marketanalog.domain.model.DataResponse
import retrofit2.Response
import javax.inject.Inject

class CatalogRepositoryImpl @Inject constructor(private val dataService: DataService): CatalogRepository {

    override suspend fun getCatalog(): Response<DataResponse>?{
        return try{
            val result = dataService.getCatalog()
            result
        } catch (e: java.net.UnknownHostException){
            null
        }
    }
}