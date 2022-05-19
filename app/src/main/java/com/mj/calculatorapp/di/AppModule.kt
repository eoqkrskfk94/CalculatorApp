package com.mj.calculatorapp.di

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

}