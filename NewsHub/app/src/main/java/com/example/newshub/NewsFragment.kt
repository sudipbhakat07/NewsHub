package com.example.newshub

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newshub.data.util.Resource
import com.example.newshub.databinding.FragmentNewsBinding
import com.example.newshub.presentation.ViewModel.NewsViewModel
import com.example.newshub.presentation.adapter.NewsAdapter
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NewsFragment : Fragment() {

    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var fragmentNewsBinding: FragmentNewsBinding
    private var country = "in"
    private var page = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        Log.i("MYTAG", "Inside onCreateView of News Fragment")
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentNewsBinding = FragmentNewsBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        newsAdapter = (activity as MainActivity).newsAdapter

        newsAdapter.setOnItemClickListener {
            Log.i("MYTAG", "NewsItem Clicked")

            val bundle = Bundle().apply {
                putSerializable("selected_item", it)
            }
            findNavController().navigate(
                R.id.action_newsFragment_to_infoFragment,
                bundle)
        }

        initRecyclerView()
        viewNewsList()
        searchView()

    }


    fun viewNewsList() {
        viewModel.getNewsHeadLines(country, page)
        viewModel.newsHeadLines.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    Log.i("MYTAG", "news ------>>>>>>  " + response.data?.articles?.size)
                    newsAdapter.differ.submitList(response.data?.articles)
                }
                is Resource.Error -> {
                    response.message?.let {
                        Toast.makeText(activity,"Error :  $it", Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    fun initRecyclerView() {
        fragmentNewsBinding.rvNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    fun showProgressBar() {
        fragmentNewsBinding.progressBar.visibility = View.VISIBLE
    }

    fun hideProgressBar() {
        fragmentNewsBinding.progressBar.visibility = View.GONE
    }
    //Search
    fun searchView() {

        fragmentNewsBinding.svNews.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                viewSearchedNews(p0.toString())
                return false
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                MainScope().launch {
                    delay(2000)
                    viewSearchedNews(p0.toString())
                }
                return false
            }
        })
        fragmentNewsBinding.svNews.setOnCloseListener(object : SearchView.OnCloseListener {
            override fun onClose(): Boolean {
                viewNewsList()
                return false
            }
        })
    }

    private fun viewSearchedNews(searchQuery: String) {
        viewModel.getSeachedNews(country, page, searchQuery)
        viewModel.searchedNews.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    Log.i("MYTAG", "news ------>>>>>>  " + response.data?.articles?.size)
                    newsAdapter.differ.submitList(response.data?.articles)

                }
                is Resource.Error -> {
//                    hideProgressBar()
                    response.message?.let {
                        Toast.makeText(activity,"Error occurred : $it", Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })

    }
}
