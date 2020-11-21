package co.grove.storefinder.viewmodel

import android.R
import co.grove.storefinder.model.CSVFile
import java.io.InputStream


class MainViewModel {

    fun loadStoreData(inputStream: InputStream) {
        val csvFile = CSVFile(inputStream)
        val storeList = csvFile.read()
    }

}