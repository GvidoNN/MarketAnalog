package my.lovely.data.api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://run.mocky.io/v3/"

@Module
@InstallIn(SingletonComponent::class)
object AsiaMenuDataObject {

    var retrofitService: AsiaMenuDataService? = null

    @Provides
    @Singleton
    fun getInstance() : AsiaMenuDataService {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        if (retrofitService == null) {
            val retrofit = Retrofit.Builder().baseUrl(BASE_URL).client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            retrofitService = retrofit.create(AsiaMenuDataService::class.java)
        }
        return retrofitService!!
    }
}