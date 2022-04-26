package com.example.hw17_1.ui.home.soon

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hw17_1.R
import com.example.hw17_1.databinding.FragmentComingSoonBinding
import com.example.hw17_1.ui.detail.DetailActivity
import com.example.hw17_1.ui.home.HomeViewModel
import com.example.hw17_1.ui.home.MovieAdapter
import com.example.hw17_1.util.MOVIE_ID
import com.example.hw17_1.util.MOVIE_TITLE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ComingSoonFragment : Fragment(R.layout.fragment_coming_soon) {
    private lateinit var binding: FragmentComingSoonBinding
    private val viewModel: HomeViewModel by viewModels()
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
        val adapter = MovieAdapter() { id,title ->
            val intent= Intent(requireActivity(), DetailActivity::class.java).apply {
                putExtra(MOVIE_ID,id)
                putExtra(MOVIE_TITLE,title)
            }
            startActivity(intent)
        }
        binding.upcomingRecycler.adapter = adapter
        binding.upcomingRecycler.layoutManager = LinearLayoutManager(requireContext())
        viewModel.upcomingMovieList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        }
        viewModel.getUpcomingMovieList()
    }
}