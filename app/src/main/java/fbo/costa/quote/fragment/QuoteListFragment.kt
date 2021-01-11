package fbo.costa.quote.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import fbo.costa.quote.R
import fbo.costa.quote.adapter.QuoteAdapter
import fbo.costa.quote.data.AppDatabase
import fbo.costa.quote.data.QuoteDao
import fbo.costa.quote.databinding.QuoteListFragmentBinding
import fbo.costa.quote.extension.navigateWithAnimations
import fbo.costa.quote.repository.DatabaseDataSource
import fbo.costa.quote.repository.QuoteRepository
import fbo.costa.quote.viewmodel.QuoteListViewModel

class QuoteListFragment : Fragment() {

    private var _binding: QuoteListFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: QuoteListViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val quoteDao: QuoteDao =
                    AppDatabase.getInstance(requireContext()).quoteDao

                val repository: QuoteRepository = DatabaseDataSource(quoteDao)
                @Suppress("UNCHECKED_CAST")
                return QuoteListViewModel(repository) as T
            }
        }
    }

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

    override fun onResume() {
        super.onResume()
        // after data insert get data from database
        viewModel.getQuotes()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModelEvents()
        configureViewListeners()
    }

    private fun observeViewModelEvents() {
        viewModel.allQuotesEvent.observe(viewLifecycleOwner) { allQuotes ->
            // Show hide icon menu
            setHasOptionsMenu(allQuotes.isNotEmpty())

            val quoteAdapter = QuoteAdapter(allQuotes) { quote ->
                val directions = QuoteListFragmentDirections
                    .actionQuoteListFragmentToQuoteFragment(quote)

                findNavController().navigateWithAnimations(directions)
            }

            with(binding.recyclerView) {
                setHasFixedSize(true)
                adapter = quoteAdapter
            }
        }
        viewModel.deleteAllQuotesEvent.observe(viewLifecycleOwner) {
            viewModel.getQuotes()
        }
    }

    private fun configureViewListeners() {
        binding.fabAdd.setOnClickListener {
            findNavController().navigateWithAnimations(R.id.action_quoteListFragment_to_quoteFragment)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.delete_subscribers) {
            viewModel.deleteAllSubscribers()
            true
        } else super.onOptionsItemSelected(item)
    }
}
