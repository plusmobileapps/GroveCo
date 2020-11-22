package co.grove.storefinder.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.grove.storefinder.R
import co.grove.storefinder.model.StoreRepo
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var storeRepo: StoreRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}