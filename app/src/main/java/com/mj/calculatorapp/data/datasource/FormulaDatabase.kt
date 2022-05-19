package com.mj.calculatorapp.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mj.calculatorapp.domain.model.Formula

@Database(
    entities = [Formula::class],
    version = 1,
    exportSchema = false
)
abstract class FormulaDatabase : RoomDatabase() {

    companion object {
        const val DB_NAME = "FormulaDatabase.db"
    }

    abstract fun formulaDao(): FormulaDao
}