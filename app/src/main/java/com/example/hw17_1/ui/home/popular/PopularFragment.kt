package com.example.hw17_1.ui.home.popular

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hw17_1.R
import com.example.hw17_1.databinding.FragmentPopularBinding
import com.example.hw17_1.ui.detail.DetailActivity
import com.example.hw17_1.ui.home.HomeViewModel
import com.example.hw17_1.ui.home.MovieAdapter
import com.example.hw17_1.util.MOVIE_ID
import com.example.hw17_1.util.MOVIE_TITLE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularFragment : Fragment(R.layout.fragment_popular) {
    lateinit var binding: FragmentPopularBinding
    private val viewModel : HomeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPopularBinding.inflate(layoutInflater)
        return binding.root
    }

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
        viewModel.popularMovieList.observe(viewLifecycleOwner){
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        }
        viewModel.getPopularMovieList()
    }
}