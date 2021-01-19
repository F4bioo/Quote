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

    fun insertOrUpdate(name: String, email: String, id: Long = 0) = viewModelScope.launch {
        if (id > 0) {
            update(id, name, email)
        } else {
            insert(name, email)
        }
    }

    private fun update(id: Long, name: String, email: String) = viewModelScope.launch {
        try {
            repository.update(id, name, email)

            _quoteStateEventData.value = QuoteState.Updated
            _messageEventData.value = R.string.quote_updated_successfully
        } catch (e: Exception) {
            _messageEventData.value = R.string.quote_error_to_insert
            Log.e(TAG, e.toString())
        }
    }

    private fun insert(name: String, email: String) = viewModelScope.launch {
        try {
            val id = repository.insert(name, email)
            if (id > 0) {
                _quoteStateEventData.value = QuoteState.Inserted
                _messageEventData.value = R.string.quote_inserted_successfully
            }
        } catch (e: Exception) {
            _messageEventData.value = R.string.quote_error_to_update
            Log.e(TAG, e.toString())
        }
    }

    fun delete(id: Long) = viewModelScope.launch {
        try {
            if (id > 0) {
                repository.delete(id)
                _quoteStateEventData.value = QuoteState.Deleted
                _messageEventData.value = R.string.quote_deleted_successfully
            }
        } catch (e: Exception) {
            _messageEventData.value = R.string.quote_error_to_delete
            Log.e(TAG, e.toString())
        }

    }

    sealed class QuoteState {
        object Inserted : QuoteState()
        object Updated : QuoteState()
        object Deleted : QuoteState()
    }

    companion object {
        private val TAG = QuoteViewModel::class.java.simpleName
    }
}
