package fbo.costa.quote.viewmodel

import android.util.Log
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

    private val _deleteAllQuotesEvent = MutableLiveData<Unit>()
    val deleteAllQuotesEvent: LiveData<Unit>
        get() = _deleteAllQuotesEvent

    private val _allQuotesEvent = MutableLiveData<List<QuoteEntity>>()
    val allQuotesEvent: LiveData<List<QuoteEntity>>
        get() = _allQuotesEvent

    fun getQuotes() = viewModelScope.launch {
        _allQuotesEvent.postValue(repository.getAll())
    }

    fun deleteAllSubscribers() = viewModelScope.launch {
        try {
            repository.deleteAll()
            _deleteAllQuotesEvent.postValue(Unit)
        } catch (ex: Exception) {
            Log.e(TAG, ex.toString())
        }
    }

    companion object {
        private val TAG = QuoteListViewModel::class.java.simpleName
    }
}
