package my.lovely.data.api

import my.lovely.domain.model.DataResponse
import retrofit2.Response
import retrofit2.http.GET

interface CatalogDataService{

    @GET("058729bd-1402-4578-88de-265481fd7d54")
    suspend fun getCatalog(): Response<DataResponse>

}