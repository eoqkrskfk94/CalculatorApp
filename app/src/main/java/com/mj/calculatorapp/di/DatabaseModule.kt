package com.mj.calculatorapp.di

import android.content.Context
import androidx.room.Room
import com.mj.calculatorapp.data.datasource.AppPreferenceManager
import com.mj.calculatorapp.data.datasource.FormulaDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDb(@ApplicationContext context: Context): FormulaDatabase =
        Room.databaseBuilder(context, FormulaDatabase::class.java, FormulaDatabase.DB_NAME).build()

    @Singleton
    @Provides
    fun provideDao(database: FormulaDatabase) = database.formulaDao()

    @Singleton
    @Provides
    fun provideAppPreference(@ApplicationContext context: Context): AppPreferenceManager =
        AppPreferenceManager(context)

}