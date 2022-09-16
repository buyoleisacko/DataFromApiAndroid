package com.example.apiapp

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.bumptech.glide.Glide
import org.json.JSONObject


private var String?.text: String
    get() {
        TODO("Not yet implemented")
    }
    set(text) {}

class MainActivity : AppCompatActivity() {
    lateinit var requestQueue: RequestQueue

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lateinit var search: Search
        lateinit var userinput: UserInput
        val appnetwork = BasicNetwork(HurlStack())
        val appcache = DiskBasedCache(cacheDir, 1024 * 1024) // 1MB cap
        requestQueue = RequestQueue(appcache, appnetwork).apply {
            start()
        }

        search.setOnClickListener {
            var input = userinput.text.toString()
            fetchData(input)
        }


    }

    private fun fetchData(input: String) {
        val url = "http://www.omdbapi.com/?t=${input}&apikey=cebd9b53"
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                if (response.get("Response") == "False") {
                    val name: String? = null

                    name.text = "Incorrect detail"
                } else {
                    val image: ImageView? = null
                    if (image != null) {
                        Glide.with(this).load(response.getString("Poster")).into(image)
                    }

                    val plot = null
                    plot.text = response.getString("Plot")
                    val name = null
                    name.text = response.getString("Title") + "\n\n" + "Writer: " + response.getString("Writer")
                }
            },
            { error ->
                Log.d("vol", error.toString())
            }
        )

        requestQueue.add(jsonObjectRequest)
    }

    private fun JsonObjectRequest(
        get: Int,
        url: String,
        nothing: Nothing?,
        function: (response: JSONObject) -> Unit,
        function1: (error: VolleyError) -> Unit
    ) {

    }
}

private fun RequestQueue.add(jsonObjectRequest: Unit) {
    TODO("Not yet implemented")
}
