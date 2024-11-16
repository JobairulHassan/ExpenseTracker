package com.example.expensetracker

import java.text.SimpleDateFormat
import java.util.Locale

object Utils {
    fun dateFormatting(dateInMillis: Long): String{
        val dateFormatter = SimpleDateFormat("dd/MM/YYYY", Locale.getDefault())
        return dateFormatter.format(dateInMillis)
    }
    fun numberFormatting(dou : Double):String {
        return String.format("%.2f", dou)
    }
}