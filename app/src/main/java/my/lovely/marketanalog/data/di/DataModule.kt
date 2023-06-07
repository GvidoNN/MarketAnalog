package my.lovely.marketanalog.data.di

import my.lovely.marketanalog.data.api.DataService
import my.lovely.marketanalog.data.repository.CatalogRepositoryImpl
import my.lovely.marketanalog.domain.repository.CatalogRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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