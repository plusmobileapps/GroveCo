package co.grove.storefinder.ui

import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import co.grove.storefinder.R
import co.grove.storefinder.model.Store
import co.grove.storefinder.model.StoreRepo
import co.grove.storefinder.network.NetworkManager
import co.grove.storefinder.util.ClosestStoreFinder
import co.grove.storefinder.viewmodel.MainViewModel
import co.grove.storefinder.viewmodel.Units
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainActivityInterface {

    @Inject
    lateinit var storeRepo: StoreRepo

    @Inject
    lateinit var networkManager: NetworkManager

    lateinit var storeData: TextView
    lateinit var errorText: TextView

    lateinit var unitsGroup: RadioGroup

    lateinit var addressText: EditText

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addressText = findViewById(R.id.addressEditText)

        storeData = findViewById(R.id.closestStoreData)
        errorText = findViewById(R.id.errorMessage)

        unitsGroup = findViewById(R.id.unitsGroup)

        unitsGroup.setOnCheckedChangeListener { _, _ ->
            val milesButton = findViewById<RadioButton>(R.id.milesButton)
            if (milesButton.isChecked) {
                viewModel.units.value = Units.MILES
            } else {
                viewModel.units.value = Units.KILOMETERS
            }
        }

        findViewById<Button>(R.id.findStoreButton).setOnClickListener {
            viewModel.addressField.value = addressText.text.toString().trim()
            viewModel.onFindStoreClicked()
        }

        viewModel.initializeObjects(networkManager, this, ClosestStoreFinder(storeRepo))
    }

    override fun onEmptyAddress() {
        val errorMessage = resources.getString(R.string.empty_address_error)
        val errorTitle = resources.getString(R.string.error_title)
        AlertDialog.Builder(this).setTitle(errorTitle).setMessage(errorMessage).show()
    }

    override fun onError(errorMessage: String) {
        storeData.visibility = GONE
        errorText.text = errorMessage
        errorText.visibility = VISIBLE
    }

    override fun onStoreFound(store: Store, distance: Double, units: Units) {
        storeData.visibility = VISIBLE
        errorText.visibility = GONE
        val distanceString = "%.2f".format(distance) + " " + units.toString()
        storeData.text = resources.getString(R.string.closest_store, store.storeName, distanceString)
    }

}