package co.grove.storefinder.ui

import co.grove.storefinder.model.Store

interface MainActivityInterface {
    fun onEmptyAddress()

    fun onError(errorMessage: String)

    fun onStoreFound(store: Store, distance: String)

}