package fbo.costa.quote.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fbo.costa.quote.data.QuoteEntity
import fbo.costa.quote.databinding.QuoteItemBinding

class QuoteAdapter(
    private val quoteList: List<QuoteEntity>,
    private val onQuoteClickListener: (subscriber: QuoteEntity) -> Unit
) : RecyclerView.Adapter<QuoteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val biding = QuoteItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return ViewHolder(biding, onQuoteClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(quoteList[position])
    }

    override fun getItemCount(): Int {
        return quoteList.size
    }

    inner class ViewHolder(
        private val binding: QuoteItemBinding,
        private val onQuoteClickListener: (quoteEntity: QuoteEntity) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(quoteEntity: QuoteEntity) {
            binding.apply {
                textQuote.text = quoteEntity.quote
                textAuthor.text = quoteEntity.author
            }

            itemView.setOnClickListener {
                onQuoteClickListener.invoke(quoteEntity)
            }
        }
    }
}
