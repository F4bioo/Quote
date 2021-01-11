package fbo.costa.quote.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import fbo.costa.quote.extension.navigateWithAnimations
import androidx.navigation.fragment.findNavController
import fbo.costa.quote.R
import fbo.costa.quote.adapter.QuoteAdapter
import fbo.costa.quote.data.AppDatabase
import fbo.costa.quote.data.QuoteDao
import fbo.costa.quote.databinding.QuoteListFragmentBinding
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
            val quoteAdapter = QuoteAdapter(allQuotes)
            Log.v("<>", "${allQuotes.size}")

            with(binding.recyclerView) {
                setHasFixedSize(true)
                adapter = quoteAdapter
            }
        }
    }

    private fun configureViewListeners() {
        binding.fabAdd.setOnClickListener {
            findNavController().navigateWithAnimations(R.id.quoteFragment)
        }
    }
}
