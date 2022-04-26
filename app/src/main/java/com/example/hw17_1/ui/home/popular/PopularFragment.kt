package com.example.hw17_1.ui.home.popular

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hw17_1.R
import com.example.hw17_1.databinding.FragmentPopularBinding
import com.example.hw17_1.ui.detail.DetailActivity
import com.example.hw17_1.ui.home.MovieAdapter
import com.example.hw17_1.util.MOVIE_ID
import com.example.hw17_1.util.MOVIE_TITLE
import com.example.hw17_1.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.math.log

@AndroidEntryPoint
class PopularFragment : Fragment(R.layout.fragment_popular) {
    lateinit var binding: FragmentPopularBinding
    private val viewModel : PopularViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPopularBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = MovieAdapter(){id,title->
            val intent= Intent(requireActivity(),DetailActivity::class.java).apply {
                putExtra(MOVIE_ID,id)
                putExtra(MOVIE_TITLE,title)
            }
            startActivity(intent)
        }
        binding.popularRecycler.adapter = adapter
        binding.popularRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.retryButton.setOnClickListener {
            binding.progressBar2.visibility = View.VISIBLE
            binding.connectionFaildText.visibility = View.GONE
            binding.retryButton.visibility = View.GONE
            viewModel.getPopularMovieList()
        }
        lifecycleScope.launch {
            viewModel.popularMovieList.collect{result->
                when(result){
                    is Resource.Success->{
                        adapter.submitList(result.data!!)
                        adapter.notifyDataSetChanged()
                        binding.progressBar2.visibility = View.GONE
                    }
                    is Resource.Error->{
                        binding.popularRecycler.visibility = View.GONE
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
        viewModel.getPopularMovieList()
    }
}