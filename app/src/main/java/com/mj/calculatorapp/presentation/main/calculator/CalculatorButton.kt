package com.mj.calculatorapp.presentation.main.calculator

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import com.mj.calculatorapp.R
import com.mj.calculatorapp.databinding.ButtonCalculatorBinding

class CalculatorButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : CardView(context, attrs) {

    private var binding: ButtonCalculatorBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.button_calculator, this, true)

    init {
        getAttrs(attrs)
    }

    private fun getAttrs(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CalculatorButton)
        setTypeArray(typedArray)
    }

    private fun setTypeArray(typedArray: TypedArray) {
        binding.textviewTitle.text = typedArray.getText(R.styleable.CalculatorButton_symbol)
        binding.background.setBackgroundResource(R.color.transparent)
        binding.cardviewButton.setCardBackgroundColor(typedArray.getColor(R.styleable.CalculatorButton_cardColor, R.color.white.toInt()))
        typedArray.recycle()
    }
}