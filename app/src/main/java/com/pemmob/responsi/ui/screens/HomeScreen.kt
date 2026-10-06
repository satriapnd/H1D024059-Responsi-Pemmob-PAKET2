package com.pemmob.responsi.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pemmob.responsi.data.model.Book
import com.pemmob.responsi.ui.components.BookItem
import com.pemmob.responsi.ui.components.ErrorView
import com.pemmob.responsi.ui.components.LoadingView
import com.pemmob.responsi.ui.components.SearchBar
import com.pemmob.responsi.ui.state.UiState
import com.pemmob.responsi.ui.viewmodel.BookViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: BookViewModel,
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit,
    onNavigateToDetail: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Katalog Buku OpenLibrary") },
                actions = {
                    IconButton(onClick = onToggleTheme) {
                        Text(
                            text = if (isDarkTheme) "☀️" else "🌙",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            SearchBar(
                onSearch = { query -> viewModel.searchBooks(query) }
            )

            when (val state = uiState) {
                is UiState.Idle -> {
                    EmptyStateText("Silakan cari buku favoritmu di atas.")
                }
                is UiState.Loading -> {
                    LoadingView()
                }
                is UiState.Success -> {
                    val books = state.data
                    if (books.isEmpty()) {
                        EmptyStateText("Buku tidak ditemukan.")
                    } else {
                        BookList(
                            books = books,
                            onBookClick = { selectedBook ->
                                viewModel.selectBook(selectedBook)
                                onNavigateToDetail()
                            }
                        )
                    }
                }
                is UiState.Error -> {
                    ErrorView(
                        message = state.message,
                        onRetry = { viewModel.retrySearch() }
                    )
                }
            }
        }
    }
}

@Composable
fun BookList(books: List<Book>, onBookClick: (Book) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(books, key = { it.key ?: it.hashCode() }) { book ->
            BookItem(book = book, onClick = { onBookClick(book) })
        }
    }
}

@Composable
fun EmptyStateText(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = message, modifier = Modifier.padding(16.dp))
    }
}
