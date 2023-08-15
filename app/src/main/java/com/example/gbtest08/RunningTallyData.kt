package com.example.gbtest08

import java.util.*

data class RunningTallyData(
    val dayTotal: Double,
    val milesDriven: Double,
    val endMileage: Int,
    val timestamp: Long,
    val currentDate: String
) {
    companion object {
        fun calculateDayTotal(expenseList: List<ExpenseData>): Double {
            return expenseList.sumByDouble { it.expenseAmount }
        }

        fun calculateMilesDriven(startMileage: Int, endMileage: Int): Double {
            return (endMileage - startMileage).toDouble()
        }
    }
}
