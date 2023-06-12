package my.lovely.data.repository

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import my.lovely.data.api.AsiaMenuDataService
import my.lovely.domain.model.AsiaResponse
import my.lovely.domain.model.Dishe
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response
import retrofit2.Retrofit

class AsiaMenuRepositoryImplTest {

    private val dataService = mock<AsiaMenuDataService>()
    private val retrofit = mock<Retrofit>()

    private val asiaMenuRepository = AsiaMenuRepositoryImpl(dataService)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `getAsiaMenu returns DataResponse`() = runTest {

        val testDataResponse = AsiaResponse(
            listOf(
                Dishe(
                    description = "test",
                    id = 1,
                    image_url = "test",
                    name = "test",
                    price = 1,
                    tegs = listOf("test"),
                    weight = 1
                )
            )
        )

        whenever(retrofit.create(AsiaMenuDataService::class.java)).thenReturn(dataService)
        whenever(dataService.getAsiaMenu()).thenReturn(Response.success(testDataResponse))

        assertEquals(
            testDataResponse,
            asiaMenuRepository.getAsianMenu()?.body()
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `getAsiaMenu from repository returns Error because of Internet`() = runTest {

        val testDataResponse = null

        whenever(retrofit.create(AsiaMenuDataService::class.java)).thenReturn(dataService)
        whenever(dataService.getAsiaMenu()).thenReturn(Response.success(testDataResponse))

        assertEquals(
            testDataResponse,
            asiaMenuRepository.getAsianMenu()?.body()
        )
    }


}