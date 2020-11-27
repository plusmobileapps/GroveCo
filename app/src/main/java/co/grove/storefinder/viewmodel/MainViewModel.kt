package co.grove.storefinder.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.grove.storefinder.model.StoreRepo
import co.grove.storefinder.network.NetworkManager
import co.grove.storefinder.ui.MainActivityInterface
import kotlinx.coroutines.launch
import java.lang.Exception

enum class Units {
    MILES,
    KILOMETERS
}

enum class AddressType {
    STREET,
    ZIPCODE
}

class MainViewModel: ViewModel() {
    var units = MutableLiveData<Units>()
    var addressType = MutableLiveData<AddressType>()
    var addressField = MutableLiveData<String>()

    lateinit var storeRepo: StoreRepo
    lateinit var networkManager: NetworkManager
    lateinit var activityInterface: MainActivityInterface

    /**
     * Normally I'd find a way to get dependency injection working here but it's tricky,
     * and is probably overkill for this example
     */
    fun initializeObjects(storeRepo: StoreRepo, networkManager: NetworkManager, activity: MainActivityInterface) {
        this.storeRepo = storeRepo
        this.networkManager = networkManager
        this.activityInterface = activity
    }

    fun onFindStoreClicked() {
        Log.e("dbug", "onFindStoreClicked")
        val address = addressField.value
        if (address != null) {
            viewModelScope.launch {
                try {
                    networkManager.requestGeocodeData(address)
                } catch (ex: Exception) {
                    activityInterface.onError(ex.toString())
                }
            }
        } else {
            activityInterface.onEmptyAddress()
        }
    }

}