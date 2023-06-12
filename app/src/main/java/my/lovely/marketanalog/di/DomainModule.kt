package my.lovely.marketanalog.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import my.lovely.domain.repository.AsiaMenuRepository
import my.lovely.domain.repository.BasketRepository
import my.lovely.domain.repository.CatalogRepository
import my.lovely.domain.usecase.*

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideCatalogUseCase(catalogRepository: CatalogRepository): GetCatalogUseCase {
        return GetCatalogUseCase(catalogRepository = catalogRepository)
    }

    @Provides
    fun provideGetAsiaMenuUseCase(asiaMenuRepository: AsiaMenuRepository): GetAsiaMenuUseCase {
        return GetAsiaMenuUseCase(asiaMenuRepository = asiaMenuRepository)
    }

    @Provides
    fun provideGetBasketDaoDBUseCase(basketReposotiry: BasketRepository): GetBasketDaoDbUseCase {
        return GetBasketDaoDbUseCase(basketRepository = basketReposotiry)
    }

    @Provides
    fun provideGetDateUseCase(): GetDateUseCase {
        return GetDateUseCase()
    }

    @Provides
    fun provideSortByTagUseCase(): SortByTagUseCase {
        return SortByTagUseCase()
    }
}