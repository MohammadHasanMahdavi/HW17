package com.example.hw17_1.ui.home.soon

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hw17_1.R
import com.example.hw17_1.databinding.FragmentComingSoonBinding
import com.example.hw17_1.ui.detail.DetailActivity
import com.example.hw17_1.ui.home.MovieAdapter
import com.example.hw17_1.util.MOVIE_ID
import com.example.hw17_1.util.MOVIE_TITLE
import com.example.hw17_1.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ComingSoonFragment : Fragment(R.layout.fragment_coming_soon) {

    private lateinit var binding: FragmentComingSoonBinding
    private val viewModel: ComingSoonViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentComingSoonBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = MovieAdapter() { id, title ->
            val intent = Intent(requireActivity(), DetailActivity::class.java).apply {
                putExtra(MOVIE_ID, id)
                putExtra(MOVIE_TITLE, title)
            }
            startActivity(intent)
        }

        binding.upcomingRecycler.adapter = adapter
        binding.upcomingRecycler.layoutManager = LinearLayoutManager(requireContext())

        lifecycleScope.launch {
            viewModel.upcomingMovieList.collect{result->
                when(result){
                    is Resource.Success->{
                        adapter.submitList(result.data!!)
                        adapter.notifyDataSetChanged()
                        binding.loadingAnimation.visibility=View.GONE
                    }
                    is Resource.Error->{
                        binding.upcomingRecycler.visibility = View.GONE
                        binding.loadingAnimation.visibility=View.GONE
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
        viewModel.getUpcomingMovieList()
    }
}