package co.grove.storefinder.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.grove.storefinder.model.StoreRepo
import co.grove.storefinder.network.NetworkManager
import co.grove.storefinder.ui.MainActivityInterface
import co.grove.storefinder.util.ClosestStoreFinder
import kotlinx.coroutines.launch

enum class Units(var displayString: String) {
    MILES("Miles"),
    KILOMETERS("Kilometers");

    override fun toString(): String {
        return displayString
    }
}

class MainViewModel : ViewModel() {
    var units = MutableLiveData<Units>()
    var addressField = MutableLiveData<String>()

    lateinit var storeRepo: StoreRepo
    lateinit var networkManager: NetworkManager
    lateinit var activityInterface: MainActivityInterface
    lateinit var closestStoreFinder: ClosestStoreFinder

    /**
     * Normally I'd find a way to get dependency injection working here but it's tricky,
     * and is probably overkill for this example
     */
    fun initializeObjects(
        storeRepo: StoreRepo,
        networkManager: NetworkManager,
        activity: MainActivityInterface
    ) {
        this.storeRepo = storeRepo
        this.networkManager = networkManager
        this.activityInterface = activity
        closestStoreFinder = ClosestStoreFinder(storeRepo)
    }

    fun onFindStoreClicked() {
        val address = addressField.value
        if (address != null) {
            viewModelScope.launch {
                try {
                    val location = networkManager.requestGeocodeData(address)
                    val lat = location.lat
                    val lon = location.lon
                    if (lat != null && lon != null) {
                        val latlong = Pair<Double, Double>(
                            lat.toDouble(),
                            lon.toDouble()
                        )
                        val unitType = units.value ?: Units.MILES

                        val storePair = closestStoreFinder.findClosestStore(latlong, unitType)
                        activityInterface.onStoreFound(storePair.first, storePair.second, unitType)
                    }
                } catch (ex: Exception) {
                    activityInterface.onError(ex.toString())
                }
            }
        } else {
            activityInterface.onEmptyAddress()
        }
    }

}