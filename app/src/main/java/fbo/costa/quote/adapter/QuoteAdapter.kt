package fbo.costa.quote.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fbo.costa.quote.R
import fbo.costa.quote.data.QuoteEntity

class QuoteAdapter(
    private val quoteList: List<QuoteEntity>,
    private val onQuoteClickListener: (subscriber: QuoteEntity) -> Unit
) : RecyclerView.Adapter<QuoteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.quote_item, parent, false)

        return ViewHolder(view, onQuoteClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(quoteList[position])
    }

    override fun getItemCount(): Int {
        return quoteList.size
    }

    inner class ViewHolder(
        itemView: View,
        private val onQuoteClickListener: (quoteEntity: QuoteEntity) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val textViewSubscriberName: TextView =
            itemView.findViewById(R.id.text_quote)
        private val textViewSubscriberEmail: TextView =
            itemView.findViewById(R.id.text_author)

        fun bind(quoteEntity: QuoteEntity) {
            textViewSubscriberName.text = quoteEntity.quote
            textViewSubscriberEmail.text = quoteEntity.author

            itemView.setOnClickListener {
                onQuoteClickListener.invoke(quoteEntity)
            }
        }
    }
}
