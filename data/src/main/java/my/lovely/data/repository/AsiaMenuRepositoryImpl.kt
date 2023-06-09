package my.lovely.data.repository

import my.lovely.data.api.AsiaMenuDataService
import my.lovely.domain.model.AsiaResponse
import my.lovely.domain.repository.AsiaMenuRepository
import retrofit2.Response
import javax.inject.Inject

class AsiaMenuRepositoryImpl@Inject constructor(private val asiaMenuDataService: AsiaMenuDataService): AsiaMenuRepository {

    override suspend fun getAsianMenu(): Response<AsiaResponse>? {
        return try{
            val result = asiaMenuDataService.getAsiaMenu()
            result
        } catch (e: java.net.UnknownHostException){
            null
        }
    }


}