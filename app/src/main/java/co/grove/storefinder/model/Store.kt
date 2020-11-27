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
        fun createStoreFromCsvRow(row: Array<String>): Store {
            // This is messy but some of the rows in the CSV are missing the last column of data.
            if (row.size == 9) {
                return Store(
                        row[0],
                        row[1],
                        row[2],
                        row[3],
                        row[4],
                        row[5],
                        row[6].toDouble(),
                        row[7].toDouble(),
                        row[8]
                )
            }
            return Store(
                    row[0],
                    row[1],
                    row[2],
                    row[3],
                    row[4],
                    row[5],
                    row[6].toDouble(),
                    row[7].toDouble(),
                    ""
            )
        }
    }
}

