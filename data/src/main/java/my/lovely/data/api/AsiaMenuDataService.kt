package my.lovely.data.api

import my.lovely.domain.model.AsiaResponse

import retrofit2.Response
import retrofit2.http.GET

interface AsiaMenuDataService {

    @GET("c7a508f2-a904-498a-8539-09d96785446e")
    suspend fun getAsiaMenu(): Response<AsiaResponse>

}