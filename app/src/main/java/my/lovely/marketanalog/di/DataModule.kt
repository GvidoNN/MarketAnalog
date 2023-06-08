package my.lovely.marketanalog.di


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import my.lovely.data.api.DataService
import my.lovely.data.repository.CatalogRepositoryImpl
import my.lovely.domain.repository.CatalogRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideCatalogRepositoryImpl(dataService: DataService) : CatalogRepository {
        return CatalogRepositoryImpl(dataService = dataService)
    }
}