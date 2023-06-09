package my.lovely.domain.repository

import my.lovely.domain.model.AsiaResponse
import retrofit2.Response

interface AsiaMenuRepository {

    suspend fun getAsianMenu(): Response<AsiaResponse>?

}