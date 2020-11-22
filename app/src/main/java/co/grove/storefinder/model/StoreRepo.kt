package co.grove.storefinder.model

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import kotlin.coroutines.suspendCoroutine

class StoreRepo(private val inputStream: InputStream) {
    private lateinit var storeList: List<Store>

    private suspend fun loadStoreData() = suspendCoroutine<Unit> {
        val reader = BufferedReader(InputStreamReader(inputStream))
        val csvFile = CSVFile(reader)
        storeList = csvFile.parseStoreList()
    }

    init {
        GlobalScope.launch {
            loadStoreData()
        }
    }
}