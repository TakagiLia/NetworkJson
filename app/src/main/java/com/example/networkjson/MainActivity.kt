package com.example.networkjson

import android.icu.text.IDNA.Info
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Response
import retrofit2.Retrofit


class MainActivity : AppCompatActivity() {

    val list = StringBuilder()
    private val contentType = "application/json".toMediaType()
    val format = Json { ignoreUnknownKeys = true }

    /*RetrofitでのJson呼び出し*/
    // Retrofit本体
    @OptIn(ExperimentalSerializationApi::class)
    private val service = Retrofit.Builder().baseUrl("http://10.0.2.2:3001/")
        .addConverterFactory(Json.asConverterFactory(contentType))
        .build()
        .create(MyService::class.java)

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        scope.launch {
//            service.getRawResponseForPosts().execute().also { response ->
//                val books = checkNotNull(response.body())
//                val textList = books.books.map{ book ->
//                    "${book.title}/${book.price}円\n"
//                }
//                val result = textList.joinToString { it }
//                findViewById<TextView>(R.id.txtResult).text = result
//            }
//        }

//        scope.launch {
//            val response = service.getRawResponseForPosts().execute()
//
//            findViewById<Button>(R.id.BookBtn).setOnClickListener {
//
//                val books = checkNotNull(response.body())
//                val textList = books.books.map{ book ->
//                    "${book.title}/${book.price}円\n"
//                }
//                val result = textList.joinToString { it }
//                findViewById<TextView>(R.id.txtResult).text = result
//
//            }
//
//            findViewById<Button>(R.id.ComicBtn).setOnClickListener {
//
//                val books = checkNotNull(response.body())
//                val textList = books.comics.map{ comic ->
//                    "${comic.title}/${comic.price}円\n"
//                }
//                val result = textList.joinToString { it }
//                findViewById<TextView>(R.id.txtResult).text = result
//
//            }
//        }

        var books: Books? = null

        scope.launch {
            val response = service.getRawResponseForPosts().execute()
            books = response.body()
        }

        val txtResult = findViewById<TextView>(R.id.txtResult)

        findViewById<Button>(R.id.BookBtn).setOnClickListener {
            val textList = books?.books
                ?.map { book ->
                    "${book.title}/${book.price}円"
                }
                ?: emptyList()

            txtResult.text = textList.joinToString("") { it }
        }

        findViewById<Button>(R.id.ComicBtn).setOnClickListener {
            val textList = books?.comics
                ?.map { comic ->
                    "${comic.title}/${comic.price}円"
                }
                ?: emptyList()

            txtResult.text = textList.joinToString("   ") { it }
        }
    }

//    fun getInfo (Btn : Button,Body : List<Books>){
//        val aa : String
//        aa = "aaa"
//
//        var result :String
//
//        scope.launch {
//            service.getRawResponseForPosts().execute().also { response ->
//                val books = checkNotNull(response.body())
//                val bookList = books.comics.map { comic ->
//                    "${comic.title}/${comic.price}円\n"
//                }
//                val comicList = books.books.map { comic ->
//                    "${comic.title}/${comic.price}円\n"
//                }
//                result = comicList.joinToString { it }
//                findViewById<TextView>(R.id.txtResult).text = result
//            }
//        }
//    }


    /*旧式のやり方*/
//    @WorkerThread
//    private suspend fun processBackground(): String {
//        return withContext(Dispatchers.IO) {
//            val result = StringBuilder()
//            val url = URL("http://10.0.2.2:3001/books")
//            val con = url.openConnection() as HttpURLConnection
//            con.requestMethod = "GET"
//
//            val reader = con.inputStream.bufferedReader()
//            reader.forEachLine {
//                result.append(it)
//            }
//
//            kotlin.runCatching {
//
//                Log.d("■result", result.toString())
//
//                val json = JSONObject(result.toString())
//                Log.d("■json", json.toString())
//                val books = json.getJSONArray("books")
//                Log.d("■books", books.toString())
//
//                for (i in 0 until books.length()) {
//                    val book = books.getJSONObject(i)
//                    list.append(book.getString("title")).append("/")
//                    list.append(book.getString("price")).append("円\n")
//                }
//
//            }.onSuccess {
//
//                Log.d("■成功", "成功")
//
//            }.onFailure {
//                Log.e("■失敗", "失敗")
//                Log.e("■失敗", it.toString())
//                throw Exception()
//            }
//            list.toString()
//        }
//    }
//    @UiThread
//    private fun postBackground(result: String) {
//        findViewById<TextView>(R.id.txtResult).text = result
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        lifecycleScope.launch {
//            val result = processBackground()
//            postBackground(result)
//        }
//    }
}