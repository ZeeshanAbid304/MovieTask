package com.example.moviedb

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviedb.Room.MovieEntity
import com.example.moviedb.adapter.MovieAdapter
import com.example.moviedb.databinding.FragmentMainBinding
import com.example.moviedb.model.MainViewModel

class MainFragment : Fragment(), MovieAdapter.MovieClickListener {
    private lateinit var binding: FragmentMainBinding
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        movieAdapter = MovieAdapter(this)
        binding.recyclerView.apply {
            adapter = movieAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }

        viewModel.fetchMoviesIfNeeded()

        viewModel.movies.observe(viewLifecycleOwner) { movieEntities ->
            movieAdapter.submitList(movieEntities)
        }
    }

    override fun onMovieClick(movie: MovieEntity) {
        val action = MainFragmentDirections.actionMainFragmentToDetailFragment(movie.id)
        findNavController().navigate(action)
    }
}