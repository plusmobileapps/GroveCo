package co.grove.storefinder.network

import android.content.Context
import android.net.Uri
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class NetworkManager(context: Context) {
    private val TAG = NetworkManager::class.simpleName

    private var queue = Volley.newRequestQueue(context)

    private val url = "https://us1.locationiq.com/v1/search.php"

    suspend fun requestGeocodeData(address: String) = suspendCoroutine<GeocodeLocations> { cont ->
        Log.e("dbug", "Requesting for address " + address)


        val stringRequest = StringRequest(
            Request.Method.GET,
            createUrl(address),
            { response ->
                try {
                    Log.e("dbug", "Here")
                    try {
                        val responseObj: Array<GeocodeLocations> = Gson().fromJson(response, Array<GeocodeLocations>::class.java)
                        cont.resume(responseObj[0])
                    } catch (ex: Exception) {
                        cont.resumeWithException(ex)
                    }
                } catch (ex: Exception) {
                    cont.resumeWithException(Exception("Invalid address"))
                }
            },
            {
                Log.e(TAG, "That didn't work:" + it.networkResponse)
                cont.resumeWithException(Exception(it.message))
            }
        )


        // Add the request to the RequestQueue.
        queue.add(stringRequest)

    }

    private fun createUrl(address: String): String {
        val key = "pk.a4de8652f90add4167de9aadd948f43f"
        val encoded = Uri.encode(address)
        return "$url?q=$encoded&key=$key&format=json"
    }

}