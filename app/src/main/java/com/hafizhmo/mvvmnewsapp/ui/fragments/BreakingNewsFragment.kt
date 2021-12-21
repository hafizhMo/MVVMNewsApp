package com.hafizhmo.mvvmnewsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hafizhmo.mvvmnewsapp.R
import com.hafizhmo.mvvmnewsapp.adapters.NewsAdapter
import com.hafizhmo.mvvmnewsapp.databinding.FragmentBreakingNewsBinding
import com.hafizhmo.mvvmnewsapp.db.ArticleDatabase
import com.hafizhmo.mvvmnewsapp.repository.NewsRepository
import com.hafizhmo.mvvmnewsapp.ui.NewsActivity
import com.hafizhmo.mvvmnewsapp.ui.NewsViewModel
import com.hafizhmo.mvvmnewsapp.ui.NewsViewModelProviderFactory
import com.hafizhmo.mvvmnewsapp.util.Resource
import kotlinx.android.synthetic.main.fragment_breaking_news.*

class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter

    val TAG = "BreakingNewsFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val newsRepository = NewsRepository(ArticleDatabase(requireContext()))
        val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)

        setupRecyclerView()

        viewModel.breakingNews.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e(TAG, "An error occured: $message")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val newsRepository = NewsRepository(ArticleDatabase(requireContext()))
//        val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)
//        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)
//
//        _binding = FragmentBreakingNewsBinding.inflate(inflater, container, false)
//        val root: View = binding.root
//
//        setupRecyclerView()
//
//        viewModel.breakingNews.observe(viewLifecycleOwner, Observer { response ->
//            when (response) {
//                is Resource.Success -> {
//                    hideProgressBar()
//                    response.data?.let { newsResponse ->
//                        newsAdapter.differ.submitList(newsResponse.articles)
//                    }
//                }
//                is Resource.Error -> {
//                    hideProgressBar()
//                    response.message?.let { message ->
//                        Log.e(TAG, "An error occured: $message")
//                    }
//                }
//                is Resource.Loading -> {
//                    showProgressBar()
//                }
//            }
//        })
//
//
//        return root
//    }

//    override fun onDestroy() {
//        super.onDestroy()
//        _binding = null
//    }

    private fun hideProgressBar() {
        paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}