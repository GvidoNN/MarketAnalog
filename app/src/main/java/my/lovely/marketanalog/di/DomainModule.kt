package my.lovely.marketanalog.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import my.lovely.domain.repository.CatalogRepository
import my.lovely.domain.usecase.GetCatalogUseCase

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideCatalogUseCase(catalogRepository: CatalogRepository): GetCatalogUseCase {
        return GetCatalogUseCase(catalogRepository = catalogRepository)
    }

}