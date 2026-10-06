package com.pemmob.responsi.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pemmob.responsi.data.model.Book
import com.pemmob.responsi.data.repository.BookRepository
import com.pemmob.responsi.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BookViewModel : ViewModel() {
    private val repository = BookRepository()

    private val _uiState = MutableStateFlow<UiState<List<Book>>>(UiState.Idle)
    val uiState: StateFlow<UiState<List<Book>>> = _uiState.asStateFlow()

    private val _selectedBook = MutableStateFlow<Book?>(null)
    val selectedBook: StateFlow<Book?> = _selectedBook.asStateFlow()

    private var currentQuery = ""

    fun searchBooks(query: String) {
        if (query.isBlank()) return
        currentQuery = query
        
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val books = repository.searchBooks(query)
                _uiState.value = UiState.Success(books)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.localizedMessage ?: "Terjadi kesalahan yang tidak diketahui")
            }
        }
    }

    fun retrySearch() {
        searchBooks(currentQuery)
    }

    fun selectBook(book: Book) {
        _selectedBook.value = book
    }
}
