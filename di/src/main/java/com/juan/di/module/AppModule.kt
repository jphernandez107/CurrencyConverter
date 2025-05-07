package com.juan.di.module

import android.app.Application
import androidx.room.Room
import com.juan.data.api.CurrencyApiService
import com.juan.data.repository.CurrencyRepositoryImpl
import com.juan.db.AppDatabase
import com.juan.db.dao.CurrencyConversionDao
import com.juan.domain.repository.CurrencyRepository
import com.juan.domain.usecase.ConvertCurrencyUseCase
import com.juan.domain.usecase.GetConversionDetailsUseCase
import com.juan.domain.usecase.GetConversionHistoryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCurrencyApi(): CurrencyApiService {
        return Retrofit.Builder()
            .baseUrl("https://api.exchangerate.host/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CurrencyApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "currency_db"
        ).build()
    }

    @Provides
    fun provideCurrencyConversionDao(db: AppDatabase): CurrencyConversionDao {
        return db.currencyConversionDao()
    }

    @Provides
    @Singleton
    fun provideCurrencyRepository(
        api: CurrencyApiService,
        dao: CurrencyConversionDao,
    ): CurrencyRepository {
        return CurrencyRepositoryImpl(api, dao)
    }

    @Provides
    @Singleton
    fun provideConvertCurrencyUseCase(
        repository: CurrencyRepository,
    ): ConvertCurrencyUseCase {
        return ConvertCurrencyUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetConversionHistoryUseCase(
        repository: CurrencyRepository,
    ): GetConversionHistoryUseCase {
        return GetConversionHistoryUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetConversionDetailsUseCase(
        repository: CurrencyRepository,
    ): GetConversionDetailsUseCase {
        return GetConversionDetailsUseCase(repository)
    }
}