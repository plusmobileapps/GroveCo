package co.grove.storefinder.ui

import co.grove.storefinder.model.Store
import co.grove.storefinder.viewmodel.Units

interface MainActivityInterface {
    fun onEmptyAddress()

    fun onError(errorMessage: String)

    fun onStoreFound(store: Store, distance: Double, units: Units)

}