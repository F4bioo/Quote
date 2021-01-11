package fbo.costa.quote.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quote")
class QuoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val quote: String,
    val author: String
)
