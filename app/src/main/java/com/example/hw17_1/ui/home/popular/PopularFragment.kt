package com.example.hw17_1.ui.home.popular

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

@AndroidEntryPoint
class PopularFragment : Fragment(R.layout.fragment_popular) {

    private lateinit var binding: FragmentPopularBinding
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

        val adapter = MovieAdapter{id,title->
            val intent= Intent(requireActivity(),DetailActivity::class.java).apply {
                putExtra(MOVIE_ID,id)
                putExtra(MOVIE_TITLE,title)
            }
            startActivity(intent)
        }

        binding.popularRecycler.adapter = adapter
        binding.popularRecycler.layoutManager = LinearLayoutManager(requireContext())

        binding.apply {
            retryButton.setOnClickListener {

                loadingAnimation.animate().apply {
                    duration = 10000
                    startDelay = 0
                }
                loadingAnimation.visibility = View.VISIBLE
                connectionFailedText.visibility = View.GONE
                retryButton.visibility = View.GONE
                popularRecycler.visibility = View.VISIBLE

                viewModel.getPopularMovieList()
            }
        }
        lifecycleScope.launch {
            viewModel.popularMovieList.collect{result->
                when(result){
                    is Resource.Success->{
                        adapter.submitList(result.data!!)
                        adapter.notifyDataSetChanged()
                        binding.loadingAnimation.visibility = View.GONE
                    }
                    is Resource.Error->{
                        binding.popularRecycler.visibility = View.GONE
                        binding.loadingAnimation.visibility = View.GONE
                        binding.connectionFailedText.visibility = View.VISIBLE
                        binding.retryButton.visibility = View.VISIBLE
                    }
                    is Resource.Loading->{
                        binding.loadingAnimation.animate().apply {
                            duration = 10000
                            startDelay = 0
                        }
                    }
                }
            }
        }
        viewModel.getPopularMovieList()
    }
}