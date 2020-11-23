package co.grove.storefinder.ui

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import co.grove.storefinder.R
import co.grove.storefinder.model.Store
import co.grove.storefinder.model.StoreRepo
import co.grove.storefinder.network.NetworkManager
import co.grove.storefinder.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainActivityInterface {

    @Inject
    lateinit var storeRepo: StoreRepo

    @Inject
    lateinit var networkManager: NetworkManager

    lateinit var storeLabel: TextView
    lateinit var storeData: TextView
    lateinit var errorText: TextView

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        storeLabel = findViewById(R.id.closestLabel)
        storeData = findViewById(R.id.closestStoreData)
        errorText = findViewById(R.id.errorMessage)

        viewModel.initializeObjects(storeRepo, networkManager, this)
    }

    override fun onEmptyAddress() {

    }

    override fun onError(errorMessage: String) {
        storeLabel.visibility = GONE
        storeData.visibility = GONE
        errorText.text = errorMessage
        errorText.visibility = VISIBLE
    }

    override fun onStoreFound(store: Store, distance: String) {
        storeLabel.visibility = VISIBLE
        storeData.visibility = VISIBLE
        errorText.visibility = GONE
        storeData.text = resources.getString(R.string.closest_store, store.storeName, distance)
    }

}