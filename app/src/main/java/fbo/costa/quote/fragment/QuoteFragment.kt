package fbo.costa.quote.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import fbo.costa.quote.data.AppDatabase
import fbo.costa.quote.data.QuoteDao
import fbo.costa.quote.databinding.QuoteFragmentBinding
import fbo.costa.quote.extension.hideKeyboard
import fbo.costa.quote.repository.DatabaseDataSource
import fbo.costa.quote.repository.QuoteRepository
import fbo.costa.quote.viewmodel.QuoteViewModel

class QuoteFragment : Fragment() {

    private var _binding: QuoteFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: QuoteViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val quoteDao: QuoteDao =
                    AppDatabase.getInstance(requireContext()).quoteDao

                val repository: QuoteRepository = DatabaseDataSource(quoteDao)
                return QuoteViewModel(repository) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = QuoteFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeEvents()
        setListeners()
    }

    private fun observeEvents() {
        viewModel.quoteStateEventData.observe(viewLifecycleOwner) { quoteState ->
            when (quoteState) {
                is QuoteViewModel.QuoteState.Inserted -> {
                    clearFields()
                    hideKeyboard()
                    requireView().requestFocus()
                }
            }
            viewModel.messageEventData.observe(viewLifecycleOwner) { stringResId ->
                Snackbar.make(requireView(), stringResId, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun clearFields() {
        binding.inputQuote.text?.clear()
        binding.inputAuthor.text?.clear()
    }

    private fun hideKeyboard() {
        val parentActivity = requireActivity()
        if (parentActivity is AppCompatActivity) {
            parentActivity.hideKeyboard()
        }
    }

    private fun setListeners() {
        binding.buttonAdd.setOnClickListener {
            val name = binding.inputQuote.text.toString()
            val email = binding.inputAuthor.text.toString()

            viewModel.addQuote(name, email)
        }
    }
}
