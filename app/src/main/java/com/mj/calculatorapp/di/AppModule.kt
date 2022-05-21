package com.mj.calculatorapp.di

import com.mj.calculatorapp.data.datasource.AppPreferenceManager
import com.mj.calculatorapp.data.datasource.FormulaDao
import com.mj.calculatorapp.data.repsitory.DefaultFormulaRepository
import com.mj.calculatorapp.domain.repository.FormulaRepository
import com.mj.calculatorapp.util.provider.DefaultDispatcherProvider
import com.mj.calculatorapp.util.provider.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDispatcherProvider(): DispatcherProvider = DefaultDispatcherProvider()


    @Singleton
    @Provides
    fun provideFormulaRepository(
        formulaDao: FormulaDao,
        appPreferenceManager: AppPreferenceManager,
        dispatcherProvider: DispatcherProvider
    ): FormulaRepository = DefaultFormulaRepository(formulaDao, appPreferenceManager, dispatcherProvider)

}