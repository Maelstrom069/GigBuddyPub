package com.example.gbtest08

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "gig_tracker.db"
        private const val DATABASE_VERSION = 19 // Increase version for the changes

        // Table names
        private const val TABLE_GIG_APPS = "gig_apps"
        private const val TABLE_RUNNING_TALLY = "running_tally"
        private const val TABLE_SELECTED_GIG_APPS = "selected_gig_apps"
        private const val TABLE_FUEL = "fuel_data"
        private const val EXPENSE_TABLE_NAME = "expenses"

        // Common column names
        private const val COLUMN_ID = "id"

        // gig_apps table column names
        private const val COLUMN_GIG_APP_NAME = "gig_app_name"

        // running_tally table column names
        private const val COLUMN_RUNNING_TALLY_ID = "running_tally_id"
        private const val COLUMN_RUNNING_TALLY_DAY_TOTAL = "day_total"
        private const val COLUMN_RUNNING_TALLY_MILES_DRIVEN = "miles_driven"
        const val COLUMN_RUNNING_TALLY_TIMESTAMP = "timestamp"
        private const val COLUMN_RUNNING_TALLY_END_MILEAGE = "end_mileage"
        const val COLUMN_RUNNING_TALLY_EXPENSE_AMOUNT = "expense_amount"
        const val COLUMN_RUNNING_TALLY_EXPENSE_TYPE = "expense_type"
        private const val COLUMN_RUNNING_TALLY_DATE = "running_tally_date"

        // fuel_data table column names
        private const val COLUMN_FUEL_ID = "fuel_id"
        private const val COLUMN_FUEL_DATE = "date"
        private const val COLUMN_FUEL_TOF_ODOMETER = "tof_odometer"
        private const val COLUMN_FUEL_GAS_PRICE = "gas_price"
        private const val COLUMN_FUEL_TOTAL_AMOUNT_PAID = "total_amount_paid"
        private const val COLUMN_FUEL_COMPLETE_FILLUP = "complete_fillup"

        // Expense table columns
        private const val COLUMN_EXPENSE_ID = "expense_id"
        private const val COLUMN_EXPENSE_DATE = "date"
        private const val COLUMN_EXPENSE_AMOUNT = "amount"
        private const val COLUMN_EXPENSE_TYPE = "type"

        // Create table statements
        private const val CREATE_TABLE_GIG_APPS =
            "CREATE TABLE IF NOT EXISTS $TABLE_GIG_APPS (" +
                    "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_GIG_APP_NAME TEXT" +
                    ")"

        private const val CREATE_TABLE_RUNNING_TALLY =
            "CREATE TABLE IF NOT EXISTS $TABLE_RUNNING_TALLY (" +
                    "$COLUMN_RUNNING_TALLY_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_RUNNING_TALLY_DAY_TOTAL REAL, " +
                    "$COLUMN_RUNNING_TALLY_MILES_DRIVEN INTEGER, " +
                    "$COLUMN_RUNNING_TALLY_TIMESTAMP DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                    "$COLUMN_RUNNING_TALLY_END_MILEAGE INTEGER, " +
                    "$COLUMN_RUNNING_TALLY_EXPENSE_AMOUNT REAL, " +
                    "$COLUMN_RUNNING_TALLY_EXPENSE_TYPE TEXT, " +
                    "$COLUMN_RUNNING_TALLY_DATE TEXT" +
                    ")"

        private const val CREATE_TABLE_SELECTED_GIG_APPS =
            "CREATE TABLE IF NOT EXISTS $TABLE_SELECTED_GIG_APPS (" +
                    "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_GIG_APP_NAME TEXT" +
                    ")"

        private const val CREATE_TABLE_FUEL =
            "CREATE TABLE IF NOT EXISTS $TABLE_FUEL (" +
                    "$COLUMN_FUEL_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_FUEL_DATE TEXT, " +
                    "$COLUMN_FUEL_TOF_ODOMETER TEXT, " +
                    "$COLUMN_FUEL_GAS_PRICE TEXT, " +
                    "$COLUMN_FUEL_TOTAL_AMOUNT_PAID TEXT, " +
                    "$COLUMN_FUEL_COMPLETE_FILLUP INTEGER" +
                    ")"

        private const val CREATE_EXPENSES_TABLE = "CREATE TABLE $EXPENSE_TABLE_NAME(" +
                    "$COLUMN_EXPENSE_ID INTEGER PRIMARY KEY," +
                    "$COLUMN_EXPENSE_DATE TEXT," +
                    "$COLUMN_EXPENSE_AMOUNT REAL," +
                    "$COLUMN_EXPENSE_TYPE TEXT" +
                    ")"

    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_GIG_APPS)
        db.execSQL(CREATE_TABLE_RUNNING_TALLY)
        db.execSQL(CREATE_TABLE_SELECTED_GIG_APPS)
        db.execSQL(CREATE_TABLE_FUEL)
        db.execSQL(CREATE_EXPENSES_TABLE)

    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Handle database upgrades if needed
    }

    fun saveStartDayData(
        odometerReading: Int,
        currentDate: Long,
        selectedGigApps: List<String>
    ) {
        val db = writableDatabase

        // Save gig apps to selected_gig_apps table
        db.delete(TABLE_SELECTED_GIG_APPS, null, null)
        selectedGigApps.forEach { gigApp ->
            val values = ContentValues()
            values.put(COLUMN_GIG_APP_NAME, gigApp)
            db.insert(TABLE_SELECTED_GIG_APPS, null, values)
        }

        // Save initial running tally data
        val values = ContentValues()
        values.put(COLUMN_RUNNING_TALLY_DAY_TOTAL, 0.0)
        values.put(COLUMN_RUNNING_TALLY_MILES_DRIVEN, 0)
        values.put(COLUMN_RUNNING_TALLY_END_MILEAGE, odometerReading)
        values.put(COLUMN_RUNNING_TALLY_TIMESTAMP, currentDate)
        db.insert(TABLE_RUNNING_TALLY, null, values)

        db.close()
    }

    fun getSelectedGigApps(): List<String> {
        val db = readableDatabase
        val projection = arrayOf(COLUMN_GIG_APP_NAME)
        val cursor = db.query(
            TABLE_SELECTED_GIG_APPS,
            projection,
            null,
            null,
            null,
            null,
            null
        )

        val gigAppsList = mutableListOf<String>()
        while (cursor.moveToNext()) {
            val gigApp = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GIG_APP_NAME))
            gigAppsList.add(gigApp)
        }

        cursor.close()
        db.close()

        return gigAppsList
    }

    fun saveRunningTallyData(
        dayTotal: Double,
        milesDriven: Int,
        endMileage: Int,
        currentDate: Long
    ) {
        val db = writableDatabase
        val values = ContentValues()
        values.put(COLUMN_RUNNING_TALLY_DAY_TOTAL, dayTotal)
        values.put(COLUMN_RUNNING_TALLY_MILES_DRIVEN, milesDriven)
        values.put(COLUMN_RUNNING_TALLY_END_MILEAGE, endMileage)
        values.put(COLUMN_RUNNING_TALLY_TIMESTAMP, currentDate) // Save currentDate to the database

        db.insert(TABLE_RUNNING_TALLY, null, values)

        db.close()
    }

    fun insertFuelData(
        date: String,
        tofOdometer: String,
        gasPrice: String,
        totalAmountPaid: String,
        completeFillup: Boolean
    ) {
        val db = writableDatabase
        val values = ContentValues()
        values.put(COLUMN_FUEL_DATE, date)
        values.put(COLUMN_FUEL_TOF_ODOMETER, tofOdometer)
        values.put(COLUMN_FUEL_GAS_PRICE, gasPrice)
        values.put(COLUMN_FUEL_TOTAL_AMOUNT_PAID, totalAmountPaid)
        values.put(COLUMN_FUEL_COMPLETE_FILLUP, if (completeFillup) 1 else 0)

        db.insert(TABLE_FUEL, null, values)
        db.close()
    }

    fun saveSelectedGigApps(selectedGigApps: List<String>) {
        val db = writableDatabase

        // Save gig apps to selected_gig_apps table
        db.delete(TABLE_SELECTED_GIG_APPS, null, null)
        selectedGigApps.forEach { gigApp ->
            val values = ContentValues()
            values.put(COLUMN_GIG_APP_NAME, gigApp)
            db.insert(TABLE_SELECTED_GIG_APPS, null, values)
        }

        db.close()
    }

    fun saveFuelData(
        date: String,
        tofOdometer: String,
        gasPrice: String,
        totalAmountPaid: String,
        completeFillup: Boolean
    ) {
        val db = writableDatabase
        val values = ContentValues()
        values.put(COLUMN_FUEL_DATE, date)
        values.put(COLUMN_FUEL_TOF_ODOMETER, tofOdometer)
        values.put(COLUMN_FUEL_GAS_PRICE, gasPrice)
        values.put(COLUMN_FUEL_TOTAL_AMOUNT_PAID, totalAmountPaid)
        values.put(COLUMN_FUEL_COMPLETE_FILLUP, if (completeFillup) 1 else 0)

        db.insert(TABLE_FUEL, null, values)
        db.close()
    }

    fun insertExpenseData(date: String, amount: String, type: String): Long {
        val values = ContentValues()
        values.put(COLUMN_EXPENSE_DATE, date)
        values.put(COLUMN_EXPENSE_AMOUNT, amount)
        values.put(COLUMN_EXPENSE_TYPE, type)

        val db = writableDatabase
        return db.insert(EXPENSE_TABLE_NAME, null, values)
    }

    // ... (Add Additional code as needed)
}
