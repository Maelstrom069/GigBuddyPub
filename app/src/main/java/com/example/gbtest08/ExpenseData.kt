package com.example.gbtest08

import android.database.Cursor

data class ExpenseData(
    val expenseAmount: Double,
    val expenseType: String,
    val timestamp: Long
) {
    companion object {
        fun fromCursor(cursor: Cursor): ExpenseData {
            val expenseAmount =
                cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_RUNNING_TALLY_EXPENSE_AMOUNT))
            val expenseType =
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_RUNNING_TALLY_EXPENSE_TYPE))
            val timestamp =
                cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_RUNNING_TALLY_TIMESTAMP))
            return ExpenseData(expenseAmount, expenseType, timestamp)
        }
    }
}
