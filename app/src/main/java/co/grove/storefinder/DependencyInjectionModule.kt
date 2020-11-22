package co.grove.storefinder

import android.content.Context
import co.grove.storefinder.model.StoreRepo
import co.grove.storefinder.network.NetworkManager
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DependencyInjectionModule {

    @Provides
    @Singleton
    fun getNetworkManager(@ApplicationContext appContext: Context): NetworkManager {
        return NetworkManager(appContext)
    }

    @Provides
    @Singleton
    fun getStoreRepo(@ApplicationContext appContext: Context): StoreRepo {
        val inputStream = appContext.resources.openRawResource(R.raw.addresses)
        return StoreRepo(inputStream)
    }

    @Provides
    @Singleton
    fun getVolleyRequestQueue(@ApplicationContext appContext: Context): RequestQueue {
        return Volley.newRequestQueue(appContext)
    }
}