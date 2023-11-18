package com.example.googlebooksretrofit.network

data class Book(
    val title: String,
    val authors: List<String>,
    val imageUrl: String
)