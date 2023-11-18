package com.example.googlebooksretrofit.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleBooksService {
    @GET("volumes")
    fun searchBooks(@Query("q") query: String): Call<BookResponse>
}

