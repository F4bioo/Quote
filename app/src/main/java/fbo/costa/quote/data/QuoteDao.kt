package fbo.costa.quote.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface QuoteDao {
    @Insert
    suspend fun insert(quote: QuoteEntity): Long

    @Update
    suspend fun update(quote: QuoteEntity)

    @Query("DELETE FROM quote WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM quote")
    suspend fun deleteAll()

    @Query("SELECT * FROM quote")
    suspend fun getAll(): List<QuoteEntity>
}
