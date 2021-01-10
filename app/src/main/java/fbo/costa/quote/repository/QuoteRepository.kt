package fbo.costa.quote.repository

import androidx.lifecycle.LiveData
import fbo.costa.quote.data.QuoteEntity

interface QuoteRepository {
    suspend fun insert(quote: String, author: String): Long

    suspend fun update(id: Long, quote: String, author: String)

    suspend fun delete(id: Long)

    suspend fun deleteAll()

    fun getAll(): LiveData<List<QuoteEntity>>
}
