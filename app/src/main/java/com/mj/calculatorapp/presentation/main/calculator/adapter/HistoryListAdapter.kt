package com.mj.calculatorapp.presentation.main.calculator.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mj.calculatorapp.databinding.ViewholderFormulaHistoryBinding
import com.mj.calculatorapp.domain.model.Formula

class HistoryListAdapter(
    private val itemClick: (Formula) -> Unit
) : ListAdapter<Formula, HistoryListAdapter.ListViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ViewholderFormulaHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding) {
            itemClick(currentList[it])
        }
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ListViewHolder(private val binding: ViewholderFormulaHistoryBinding, clickAtPosition: (Int) -> Unit) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.constraintlayoutSearchHistory.setOnClickListener {
                clickAtPosition(adapterPosition)
            }
        }


        fun bind(formula: Formula) = with(binding) {
            textviewTitle.text = formula.content
            //constraintlayoutSearchHistory.setOnClickListener { itemClick(formula) }
        }
    }

    companion object {

        private val diffCallback = object : DiffUtil.ItemCallback<Formula>() {

            override fun areItemsTheSame(oldItem: Formula, newItem: Formula): Boolean {
                return oldItem.content == newItem.content
            }

            override fun areContentsTheSame(oldItem: Formula, newItem: Formula): Boolean {
                return oldItem == newItem
            }
        }
    }


}