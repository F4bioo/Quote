package fbo.costa.quote.viewmodel

import androidx.lifecycle.ViewModel
import fbo.costa.quote.repository.QuoteRepository

class QuoteListViewModel(
    private val repository: QuoteRepository
) : ViewModel() {

    val allQuoteEvent = repository.getAll()
}
