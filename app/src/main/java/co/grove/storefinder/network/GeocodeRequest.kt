package co.grove.storefinder.network

import android.net.Uri
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest

class GeocodeRequest(
    private val address: String,
    urlString: String,
    listener: Response.Listener<String>,
    errorListener: Response.ErrorListener
) :
    StringRequest(
        Method.GET,
        urlString,
        listener, errorListener
    ) {

    override fun getParams(): MutableMap<String, String> {
        val params = HashMap<String, String>()
        val encoded = Uri.encode(address)
        params["address"] = encoded
        params["key"] = "pk.a4de8652f90add4167de9aadd948f43f"
        params["format"] = "json"
        return params
    }
}