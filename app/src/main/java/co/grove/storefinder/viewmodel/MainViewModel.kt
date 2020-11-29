package co.grove.storefinder.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.grove.storefinder.network.GeocodeLocations
import co.grove.storefinder.network.NetworkManager
import co.grove.storefinder.ui.MainActivityInterface
import co.grove.storefinder.util.ClosestStoreFinder
import kotlinx.coroutines.launch
import java.lang.Exception

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

    lateinit var networkManager: NetworkManager
    lateinit var activityInterface: MainActivityInterface
    lateinit var closestStoreFinder: ClosestStoreFinder

    /**
     * Normally I'd find a way to get dependency injection working here but it's tricky,
     * and is probably overkill for this example
     */
    fun initializeObjects(
        networkManager: NetworkManager,
        activity: MainActivityInterface,
        closestStoreFinder: ClosestStoreFinder
    ) {
        this.networkManager = networkManager
        this.activityInterface = activity
        this.closestStoreFinder = closestStoreFinder
    }

    fun onFindStoreClicked() {
        val address = addressField.value
        if (address != null && address.isNotEmpty()) {
            viewModelScope.launch {
                val location = networkManager.requestGeocodeData(address)
                val latlong = convertGeocodeLocationsToLatLongPair(location)
                if (latlong != null) {
                    val unitType = units.value ?: Units.MILES
                    val storePair = closestStoreFinder.findClosestStore(latlong, unitType)
                    activityInterface.onStoreFound(storePair.first, storePair.second, unitType)
                } else {
                    activityInterface.onError("Could not find address")
                }
            }
        } else {
            activityInterface.onEmptyAddress()
        }
    }

    @VisibleForTesting
    fun convertGeocodeLocationsToLatLongPair(geocodeData: GeocodeLocations): Pair<Double, Double>? {
        val lat = geocodeData.lat
        val lon = geocodeData.lon
        var pair: Pair<Double, Double>? = null
        if (lat != null && lon != null) {
            pair = try {
                Pair(lat.toDouble(), lon.toDouble())
            } catch (ex: Exception) {
                null
            }
        }
        return pair
    }

}