package my.lovely.data.repository

import android.util.Log
import my.lovely.data.api.CatalogDataService
import my.lovely.domain.repository.CatalogRepository
import my.lovely.domain.model.DataResponse
import retrofit2.Response
import javax.inject.Inject

class CatalogRepositoryImpl@Inject constructor(private val dataService: CatalogDataService):
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