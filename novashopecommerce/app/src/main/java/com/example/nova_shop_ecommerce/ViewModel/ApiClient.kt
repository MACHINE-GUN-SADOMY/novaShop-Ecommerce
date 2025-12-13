package com.example.nova_shop_ecommerce.ViewModel

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    // CAMBIAR IP A LA QUE SE ESTA EJECUTANDO LA API
    // SI ES LOCALHOST USAR IPCONFIG O IFCONFIG
    private const val BASE_URL = "http://192.168.100.3:8081/api/"

    private val client = OkHttpClient.Builder().build()

    val api: ApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
        .create(ApiService::class.java)
}