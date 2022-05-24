package com.mj.calculatorapp.data.mapper

import com.mj.calculatorapp.data.model.FormulaEntity
import com.mj.calculatorapp.domain.model.Formula

//FormulaEntity -> Formula
fun mapperToFormula(formulaEntities: List<FormulaEntity>): List<Formula> {
    return formulaEntities.toList().map {
        Formula(
            it.content
        )
    }
}

fun FormulaEntity.toFormula() = Formula(
    content
)

//Formula -> FormulaEntity
fun mapperToFormulaEntity(formulas: List<FormulaEntity>): List<FormulaEntity> {
    return formulas.map {
        FormulaEntity(
            it.content
        )
    }
}

fun Formula.toFormulaEntity() = FormulaEntity(
    content
)