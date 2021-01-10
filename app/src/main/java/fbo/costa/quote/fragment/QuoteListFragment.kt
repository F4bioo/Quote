package fbo.costa.quote.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import fbo.costa.quote.adapter.QuoteAdapter
import fbo.costa.quote.data.QuoteEntity
import fbo.costa.quote.databinding.QuoteListFragmentBinding
import fbo.costa.quote.viewmodel.QuoteListViewModel

class QuoteListFragment : Fragment() {

    private var _binding: QuoteListFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: QuoteListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = QuoteListFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val quoteAdapter = QuoteAdapter(
            listOf(
                QuoteEntity(
                    1,
                    "The way to get started is to quit talking and begin doing.",
                    "-Walt Disney"
                ),
                QuoteEntity(
                    2,
                    "If life were predictable it would cease to be life, and be without flavor.",
                    "-Eleanor Roosevelt"
                )
            )
        )

        binding.recyclerView.run {
            setHasFixedSize(true)
            adapter = quoteAdapter
        }
    }
}
