package co.grove.storefinder.model

import android.content.Context
import co.grove.storefinder.R
import co.grove.storefinder.model.Store.Companion.createStoreFromCsvRow
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.NumberFormatException
import java.lang.RuntimeException
import java.util.ArrayList
import javax.inject.Inject

class StoreCache @Inject constructor(@ApplicationContext private val context: Context) {
    fun parseStoreList(): List<Store> {
        val inputStream = context.resources.openRawResource(R.raw.addresses)
        val inputStreamReader = InputStreamReader(inputStream)
        val reader = BufferedReader(inputStreamReader)
        val csvFile = CSVFile(reader)
        return csvFile.parseStoreList()
    }
}