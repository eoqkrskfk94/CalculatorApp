package com.mj.calculatorapp.data.datasource

import androidx.room.*
import com.mj.calculatorapp.domain.model.Formula

@Dao
interface FormulaDao {

    @Query("SELECT * FROM formulas")
    suspend fun getAll(): List<Formula>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(formula: Formula)

    @Query("DELETE FROM formulas")
    suspend fun deleteAll()

}