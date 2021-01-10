package fbo.costa.quote.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fbo.costa.quote.R
import fbo.costa.quote.repository.QuoteRepository
import kotlinx.coroutines.launch

class QuoteViewModel(
    private val repository: QuoteRepository
) : ViewModel() {

    private val _quoteStateEventData = MutableLiveData<QuoteState>()
    val quoteStateEventData: LiveData<QuoteState>
        get() = _quoteStateEventData

    private val _messageEventData = MutableLiveData<Int>()
    val messageEventData: LiveData<Int>
        get() = _messageEventData

    fun addQuote(name: String, email: String) = viewModelScope.launch {
        try {
            val id = repository.insert(name, email)
            if (id > 0) {
                _quoteStateEventData.value = QuoteState.Inserted
                _messageEventData.value = R.string.quote_inserted_successfully
            }
        } catch (ex: Exception) {
            _messageEventData.value = R.string.quote_error_to_insert
            Log.e(TAG, ex.toString())
        }
    }

    sealed class QuoteState {
        object Inserted : QuoteState()
    }

    companion object {
        private val TAG = QuoteViewModel::class.java.simpleName
    }
}
