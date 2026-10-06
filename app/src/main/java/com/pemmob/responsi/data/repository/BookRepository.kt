package com.pemmob.responsi.data.repository

import com.pemmob.responsi.data.model.Book
import com.pemmob.responsi.data.remote.ApiClient

class BookRepository {
    private val api = ApiClient.instance

    suspend fun searchBooks(query: String): List<Book> {
        val response = api.searchBooks(query)
        // Null safety menggunakan elvis operator ?:
        return response.docs ?: emptyList()
    }
}
