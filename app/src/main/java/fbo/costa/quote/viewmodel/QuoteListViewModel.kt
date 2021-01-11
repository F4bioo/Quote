package fbo.costa.quote.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fbo.costa.quote.data.QuoteEntity
import fbo.costa.quote.repository.QuoteRepository
import kotlinx.coroutines.launch

class QuoteListViewModel(
    private val repository: QuoteRepository
) : ViewModel() {

    private val _allQuotesEvent = MutableLiveData<List<QuoteEntity>>()
    val allQuotesEvent: LiveData<List<QuoteEntity>>
        get() = _allQuotesEvent

    fun getQuotes() = viewModelScope.launch {
        _allQuotesEvent.postValue(repository.getAll())
    }
}
