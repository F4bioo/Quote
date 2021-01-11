package fbo.costa.quote.repository

import androidx.lifecycle.LiveData
import fbo.costa.quote.data.QuoteDao
import fbo.costa.quote.data.QuoteEntity

class DatabaseDataSource(
    private val quoteDao: QuoteDao
) : QuoteRepository {

    override suspend fun insert(quote: String, author: String): Long {
        val quoteEntity = QuoteEntity(
            quote = quote,
            author = author
        )
        return quoteDao.insert(quoteEntity)
    }

    override suspend fun update(id: Long, quote: String, author: String) {
        val quoteEntity = QuoteEntity(
            id = id,
            quote = quote,
            author = author
        )
        quoteDao.update(quoteEntity)
    }

    override suspend fun delete(id: Long) {
        quoteDao.delete(id)
    }

    override suspend fun deleteAll() {
        quoteDao.deleteAll()
    }

    override suspend fun getAll(): LiveData<List<QuoteEntity>> {
        return quoteDao.getAll()
    }
}
