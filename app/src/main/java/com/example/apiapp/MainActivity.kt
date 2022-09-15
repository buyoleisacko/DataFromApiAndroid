package com.example.apiapp

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import java.util.jar.Attributes


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

    fun fetchData(input: String) {
        val url = "http://www.omdbapi.com/?t=${input}&apikey=cebd9b53"
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                if (response.get("Response") == "False") {
                    val name = Attributes.Name
                    name.text = "Incorrect detail"
                } else {
                    Glide.with(this).load(response.getString("Poster")).into(image)
                    plot.text = response.getString("Plot")
                    name.text =
                        response.getString("Title") + "\n\n" + "Writer: " + response.getString("Writer")
                }
            },
            { error ->
                Log.d("vol", error.toString())
            }
        )

        requestQueue.add(jsonObjectRequest)
    }
}