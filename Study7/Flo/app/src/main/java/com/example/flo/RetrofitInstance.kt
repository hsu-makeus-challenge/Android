package com.example.flo

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun getRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://aos.inyro.site")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
