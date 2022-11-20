@file:Suppress("OverrideDeprecatedMigration", "OverrideDeprecatedMigration")

package com.udacity.asteroidradar.main

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(
            this,
            RepositoryFactory(requireActivity().application)
        )[MainViewModel::class.java]
    }

    private val asteroidAdapter =
        AsteroidAdapter(AsteroidAdapter.AsteroidListener { asteroid ->
            viewModel.onClicked(asteroid)
        })

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        setHasOptionsMenu(true)


        binding.apply {
            asteroidRecycler.apply {
                adapter = asteroidAdapter
                layoutManager = LinearLayoutManager(this.context)
            }

        }
        viewModel.list.observe(viewLifecycleOwner, Observer<List<Asteroid>> { asteroid ->
            asteroid.apply {
                asteroidAdapter.submitList(this)
            }
        })


        viewModel.navigateToDetailAsteroid.observe(viewLifecycleOwner, Observer { asteroid ->
            if (asteroid != null) {
                this.findNavController().navigate(MainFragmentDirections.actionShowDetail(asteroid))
                viewModel.navigateToDetail()
            }
        })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}
