package br.com.lucashsouza.fitnesstracker

import br.com.lucashsouza.fitnesstracker.model.Calc

interface OnListClickListener {
    fun onClick(id: Int, type: String)
    fun onLongClick(position: Int, calc: Calc)
}