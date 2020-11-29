package co.grove.storefinder.model

import org.junit.Test

class StoreTest {

    private val storeName = "Crystal"
    private val storeLocation = "SWC Broadway & Bass Lake Rd"
    private val address = "5537 W Broadway Ave"
    private val city = "Crystal"
    private val state = "MN"
    private val zip = "55428-3507"
    private val lat = "45.0521539"
    private val long = "-93.364854"
    private val county = "Hennepin County"

    @Test
    fun `createStoreFromCsvRow correctly parses data`() {
        // GIVEN
        val strings = arrayOf(storeName,
                storeLocation,
                address,
                city,
                state,
                zip,
                lat,
                long,
                county)

        // WHEN
        val store = Store.createStoreFromCsvRow(strings)

        // THEN
        assert(store.storeName == storeName)
        assert(store.storeLocation == storeLocation)
        assert(store.address == address)
        assert(store.city == city)
        assert(store.state == state)
        assert(store.zip == zip)
        assert(store.lat == lat.toDouble())
        assert(store.long == long.toDouble())
        assert(store.county == county)
    }

    @Test
    fun `createStoreFromCsvRow handles missing county`() {
        // GIVEN
        val strings = arrayOf(storeName,
                storeLocation,
                address,
                city,
                state,
                zip,
                lat,
                long)

        // WHEN
        val store = Store.createStoreFromCsvRow(strings)

        // THEN
        assert(store.storeName == storeName)
        assert(store.storeLocation == storeLocation)
        assert(store.address == address)
        assert(store.city == city)
        assert(store.state == state)
        assert(store.zip == zip)
        assert(store.lat == lat.toDouble())
        assert(store.long == long.toDouble())
        assert(store.county == "")
    }
}