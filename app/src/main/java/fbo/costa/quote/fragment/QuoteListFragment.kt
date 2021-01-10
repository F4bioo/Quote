package fbo.costa.quote.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import fbo.costa.quote.R
import fbo.costa.quote.databinding.QuoteFragmentBinding
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(QuoteListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}