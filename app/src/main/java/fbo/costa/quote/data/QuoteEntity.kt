package fbo.costa.quote.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "quote")
class QuoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val quote: String,
    val author: String
) : Parcelable
