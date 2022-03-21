package co.grove.storefinder.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.grove.storefinder.model.Store
import co.grove.storefinder.network.GeocodeLocations
import co.grove.storefinder.network.NetworkManager
import co.grove.storefinder.ui.MainActivityInterface
import co.grove.storefinder.util.ClosestStoreFinder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

enum class Units(var displayString: String) {
    MILES("Miles"),
    KILOMETERS("Kilometers");

    override fun toString(): String {
        return displayString
    }
}

sealed class MainState {
    object EmptyAddress : MainState()
    data class Error(val message: String) : MainState()
    data class StoreFound(val store: Store, val distance: Double, val units: Units) : MainState()
}

@HiltViewModel
class MainViewModel @Inject constructor(
    private val networkManager: NetworkManager,
    private val closestStoreFinder: ClosestStoreFinder,
): ViewModel() {

    private val _state = MutableLiveData<MainState>()
    val state: LiveData<MainState> get() = _state

    var units = MutableLiveData<Units>()
    var addressField = MutableLiveData<String>()

    fun onFindStoreClicked() {
        val address = addressField.value
        if (address != null && address.isNotEmpty()) {
            viewModelScope.launch {
                val location = networkManager.requestGeocodeData(address)
                val latlong = convertGeocodeLocationsToLatLongPair(location)
                val newState = if (latlong != null) {
                    val unitType = units.value ?: Units.MILES
                    val storePair = closestStoreFinder.findClosestStore(latlong, unitType)
                    MainState.StoreFound(storePair.first, storePair.second, unitType)
                } else {
                    MainState.Error("Could not find address")
                }
                _state.postValue(newState)
            }
        } else {
            _state.value = MainState.EmptyAddress
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