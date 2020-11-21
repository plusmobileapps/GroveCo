package co.grove.storefinder.network

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class NetworkManager(private val context: Context) {
    private val TAG = NetworkManager::class.simpleName
    private val queue = Volley.newRequestQueue(context)
    private val url = "http://rickandmortyapi.com/api/character/"

    suspend fun requestGeocodeData() = suspendCoroutine<GeocodeResponse> { cont ->
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val root = Gson().fromJson(response, GeocodeResponse::class.java)
                cont.resume(root)
            },
            {
                Log.e(TAG, "That didn't work:" + it.networkResponse)
                cont.resumeWithException(Exception(it.message))
            }
        )

        // Add the request to the RequestQueue.
        queue.add(stringRequest)

    }
}