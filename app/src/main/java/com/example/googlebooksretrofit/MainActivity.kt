package com.example.googlebooksretrofit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.googlebooksretrofit.ui.theme.GoogleBooksRetrofitTheme
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.googlebooksretrofit.network.Book
import com.example.googlebooksretrofit.network.BookResponse
import com.example.googlebooksretrofit.network.GoogleBooksService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//Extension setBooks empty
private fun <BookAdapter> BookAdapter.setBooks(items: List<Book>) {

}

class MainActivity<BookAdapter> : ComponentActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BookAdapter
    //private lateinit var adapter: BookAdapter = BookAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        //adapter = BookAdapter()
        recyclerView.adapter = adapter

        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/books/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(GoogleBooksService::class.java)

        val call = service.searchBooks("android") // Query for "android" books, change as needed

        call.enqueue(object : Callback<BookResponse> {
            override fun onResponse(call: Call<BookResponse>, response: Response<BookResponse>) {
                if (response.isSuccessful) {
                    val bookResponse = response.body()
                    bookResponse?.let {
                        adapter.setBooks(it.items)
                    }
                }
            }
            override fun onFailure(call: Call<BookResponse>, t: Throwable) {
                // Handle failure
            }
        })
    }
        //

    /*
            setContent {
            GoogleBooksRetrofitTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }

     */
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GoogleBooksRetrofitTheme {
        Greeting("Android")
    }
}