package my.lovely.data.repository

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import my.lovely.data.api.CatalogDataService
import my.lovely.domain.model.Catalog
import my.lovely.domain.model.DataResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response
import retrofit2.Retrofit

class CatalogRepositoryImplTest {

    private val dataService = mock<CatalogDataService>()
    private val retrofit = mock<Retrofit>()

    private val catalogRepository = CatalogRepositoryImpl(dataService)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `getCatalog returns DataResponse`() = runTest {

        val testDataResponse = DataResponse(listOf(Catalog(id = 0, image_url = "test", name = "test")))

        whenever(retrofit.create(CatalogDataService::class.java)).thenReturn(dataService)
        whenever(dataService.getCatalog()).thenReturn(Response.success(testDataResponse))

        assertEquals(
            testDataResponse,
            catalogRepository.getCatalog()?.body()
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `getCatalog from repository returns Error because of Internet`() = runTest {

        val testDataResponse = null

        whenever(retrofit.create(CatalogDataService::class.java)).thenReturn(dataService)
        whenever(dataService.getCatalog()).thenReturn(Response.success(testDataResponse))

        assertEquals(
            testDataResponse,
        catalogRepository.getCatalog()?.body()
        )
    }



}