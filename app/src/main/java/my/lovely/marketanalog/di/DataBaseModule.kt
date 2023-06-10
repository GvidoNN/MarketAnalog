package my.lovely.marketanalog.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import my.lovely.data.database.BasketDataBase
import my.lovely.domain.database.BasketDao


@InstallIn(SingletonComponent::class)
@Module

class DataBaseModule {

    @Provides
    fun provideBasketDao(basketDatabase: BasketDataBase): BasketDao {
        return basketDatabase.BasketDao()
    }
}