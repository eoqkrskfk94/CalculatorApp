package com.mj.calculatorapp.data.database

import androidx.room.*
import com.mj.calculatorapp.data.model.FormulaEntity

@Dao
interface FormulaDao {

    @Query("SELECT * FROM formulas")
    suspend fun getAll(): List<FormulaEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(formula: FormulaEntity)

    @Query("DELETE FROM formulas")
    suspend fun deleteAll()

}