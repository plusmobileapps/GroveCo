package co.grove.storefinder

import android.content.Context
import co.grove.storefinder.model.StoreRepo
import co.grove.storefinder.network.NetworkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ApplicationComponent::class)
class DI {

    @Provides
    fun getNetworkManager(@ApplicationContext appContext: Context): NetworkManager {
        return NetworkManager(appContext)
    }

    @Provides
    fun getStoreRepo(): StoreRepo {
        return StoreRepo
    }
}