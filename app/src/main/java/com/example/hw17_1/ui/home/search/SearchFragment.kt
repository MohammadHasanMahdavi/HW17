package com.example.hw17_1.ui.home.search

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hw17_1.R
import com.example.hw17_1.databinding.FragmentSearchBinding
import com.example.hw17_1.ui.detail.DetailActivity
import com.example.hw17_1.ui.home.MovieAdapter
import com.example.hw17_1.util.MOVIE_ID
import com.example.hw17_1.util.MOVIE_TITLE
import com.example.hw17_1.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {
    private lateinit var binding: FragmentSearchBinding
    private val viewModel : SearchViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = MovieAdapter(){id,title->
            val intent= Intent(requireActivity(), DetailActivity::class.java).apply {
                putExtra(MOVIE_ID,id)
                putExtra(MOVIE_TITLE, title)
            }
            startActivity(intent)
        }
        binding.searchRecycler.adapter = adapter
        binding.searchRecycler.layoutManager = LinearLayoutManager(requireContext())
        lifecycleScope.launch {
            viewModel.searchResult.collect{result->
                when(result){
                    is Resource.Success->{
                        adapter.submitList(result.data!!)
                        adapter.notifyDataSetChanged()
                        binding.progressBar2.visibility = View.GONE
                    }
                    is Resource.Error->{
                        binding.searchRecycler.visibility = View.GONE
                        binding.progressBar2.visibility = View.GONE
                        binding.connectionFaildText.visibility = View.VISIBLE
                        binding.retryButton.visibility = View.VISIBLE
                    }
                    is Resource.Loading->{
                        binding.progressBar2.visibility = View.VISIBLE
                    }
                }
            }
        }
        binding.progressBar2.visibility = View.GONE
        binding.searchButton.setOnClickListener {
            val searchQuery = binding.searchEditText.text.toString()
            if (searchQuery.isNotBlank()){
                viewModel.searchMovieList(searchQuery)
            }else{
                Toast.makeText(requireContext(), "Please enter a valid text.", Toast.LENGTH_SHORT).show()
            }

        }
    }

}