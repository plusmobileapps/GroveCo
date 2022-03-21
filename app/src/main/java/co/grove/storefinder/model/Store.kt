package co.grove.storefinder.model

data class Store(
    val storeName: String,
    val storeLocation: String,
    val address: String,
    val city: String,
    val state: String,
    val zip: String,
    val lat: Double,
    val long: Double,
    val county: String
) {

    companion object {
        fun createStoreFromCsvRow(row: Array<String>): Store =
            // This is messy but some of the rows in the CSV are missing the last column of data.
            if (row.size == 9) {
                Store(
                    storeName = row[0],
                    storeLocation = row[1],
                    address = row[2],
                    city = row[3],
                    state = row[4],
                    zip = row[5],
                    lat = row[6].toDouble(),
                    long = row[7].toDouble(),
                    county = row[8]
                )
            } else Store(
                storeName = row[0],
                storeLocation = row[1],
                address = row[2],
                city = row[3],
                state = row[4],
                zip = row[5],
                lat = row[6].toDouble(),
                long = row[7].toDouble(),
                county = ""
            )
    }
}

