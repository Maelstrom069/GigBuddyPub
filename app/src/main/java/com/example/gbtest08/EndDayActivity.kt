package com.example.gbtest08

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat

class EndDayActivity : AppCompatActivity() {
    private lateinit var selectedGigApps: List<String>
    private var expenseAmount: Double = 0.0
    private var dayTotal: Double = 0.0
    private var milesDriven: Int = 0
    private var startDayOdometer: Int = 0
    private lateinit var expensesTextView: TextView
    private lateinit var netTotalTextView: TextView
    private lateinit var milesDrivenTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end_day)
        expensesTextView = findViewById(R.id.expensesTextView)
        netTotalTextView = findViewById(R.id.netTotalTextView)
        milesDrivenTextView = findViewById(R.id.milesDrivenTextView)

        val dbHelper = DatabaseHelper(this)
        val tableLayout: TableLayout = findViewById(R.id.gigAppTableLayout)

        val selectedGigAppsString = dbHelper.getSelectedGigApps()
        selectedGigApps = dbHelper.getSelectedGigApps()

        expenseAmount = intent.getDoubleExtra("expenseAmount", 0.0)
        dayTotal = intent.getDoubleExtra("dayTotal", 0.0)
        milesDriven = intent.getIntExtra("milesDriven", 0)
        startDayOdometer = intent.getIntExtra("startDayOdometer", 0)

        val gigAppAmounts = intent.getDoubleArrayExtra("gigAppAmounts") ?: DoubleArray(selectedGigApps.size)

        populateGigAppTable(gigAppAmounts)
        updateSummary()
    }

    private fun populateGigAppTable(gigAppAmounts: DoubleArray) {
        val tableLayout = findViewById<TableLayout>(R.id.gigAppTableLayout)

        for ((index, gigApp) in selectedGigApps.withIndex()) {
            val tableRow = TableRow(this)
            tableRow.gravity = Gravity.CENTER_VERTICAL
            tableRow.setPadding(0, 10, 0, 10)

            val gigAppTextView = TextView(this)
            gigAppTextView.text = gigApp
            gigAppTextView.setTextColor(Color.BLACK)
            gigAppTextView.setBackgroundResource(R.drawable.table_cell_border)

            val amountTextView = TextView(this)
            val gigAppAmount = gigAppAmounts[index]
            amountTextView.text = DecimalFormat("$#.00").format(gigAppAmount)
            amountTextView.setTextColor(Color.BLACK)
            amountTextView.setBackgroundResource(R.drawable.table_cell_border)

            val layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
            gigAppTextView.layoutParams = layoutParams
            amountTextView.layoutParams = layoutParams

            tableRow.addView(gigAppTextView)
            tableRow.addView(amountTextView)

            tableLayout.addView(tableRow)
        }
    }

    private fun updateSummary() {
        expensesTextView.text = DecimalFormat("$#.00").format(expenseAmount)
        netTotalTextView.text = DecimalFormat("$#.00").format(dayTotal - expenseAmount)
        milesDrivenTextView.text = milesDriven.toString()
    }
}
