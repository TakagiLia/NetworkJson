package com.example.networkjson

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Book(
    val isbn: String,
    val title: String,
    val author: String,
    val published: String,
    val price: String
)

@Serializable
data class Comic(
    val title: String,
    val author: String,
    val published: String,
    val price: String
)

@Serializable
data class Books(
    val books: List<Book>,
    val name: String,
    val comics: List<Comic>
)
