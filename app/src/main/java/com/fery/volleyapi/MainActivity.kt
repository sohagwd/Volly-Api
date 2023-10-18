package com.fery.volleyapi

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder

class MainActivity : AppCompatActivity() {
    val url = "https://api.github.com/users"
    var userInfoItem = arrayOf<UserInfoItem>()
    var userInfo = UserInfo()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val stringRequest = StringRequest(
            url,
            Response.Listener {
                val gsonBuilder = GsonBuilder()
                val gson = gsonBuilder.create()
                userInfoItem = gson.fromJson(it, Array<UserInfoItem>::class.java)
                userInfoItem.forEach {
                    userInfo.add(it)
                }
                val adapter = Adapter(this, userInfo)
                recyclerView.layoutManager  = LinearLayoutManager(this)
                recyclerView.adapter = adapter
            },
            Response.ErrorListener {
                Toast.makeText(this, "Something went wrong " + it.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
        )
        val volley = Volley.newRequestQueue(this)
        volley.add(stringRequest)
    }
}