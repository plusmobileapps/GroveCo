package co.grove.storefinder.model

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.suspendCoroutine

@Singleton
class StoreRepo @Inject constructor(private val storeCache: StoreCache) {
    private var storeList: List<Store> = emptyList()

    fun getStoreCount(): Int {
        return storeList.size
    }

    fun getStore(index: Int): Store {
        return storeList[index]
    }

    init {
        GlobalScope.launch {
            loadStoreData()
        }
    }

    private suspend fun loadStoreData() {
        storeList = storeCache.parseStoreList()
    }
}