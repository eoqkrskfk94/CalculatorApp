package com.mj.calculatorapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "formulas")
data class Formula(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val content: String
)