package co.grove.storefinder.ui

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import co.grove.storefinder.R
import co.grove.storefinder.databinding.ActivityMainBinding
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

    lateinit var storeData: TextView
    lateinit var errorText: TextView

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel

        storeData = findViewById(R.id.closestStoreData)
        errorText = findViewById(R.id.errorMessage)

        viewModel.initializeObjects(storeRepo, networkManager, this)
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

    override fun onStoreFound(store: Store, distance: String) {
        storeData.visibility = VISIBLE
        errorText.visibility = GONE
        storeData.text = resources.getString(R.string.closest_store, store.storeName, distance)
    }

}