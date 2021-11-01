package com.example.newshub

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newshub.data.model.Article
import com.example.newshub.databinding.FragmentSavedBinding
import com.example.newshub.presentation.ViewModel.NewsViewModel
import com.example.newshub.presentation.adapter.NewsAdapter
import com.google.android.material.snackbar.Snackbar

class SavedFragment : Fragment() {
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var fragmentSavedBinding: FragmentSavedBinding
    private lateinit var newsList: List<Article>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("MYTAG", "Inside onViewCreated of News Fragment")
        fragmentSavedBinding = FragmentSavedBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        newsAdapter = (activity as MainActivity).newsAdapter
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("selected_item",it)
            }
            findNavController().navigate(
                R.id.action_savedFragment_to_infoFragment,
                bundle)
        }
        initRecyclerView()
        viewModel.getSavedNewsFromDB().observe(viewLifecycleOwner, {
            newsAdapter.differ.submitList(it)
        })

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsAdapter.differ.currentList[position]
                viewModel.deleteArticle(article)

                Snackbar.make(view,"Deleted Successfully",Snackbar.LENGTH_LONG)
                    .apply {
                        setAction("Undo") {
                        viewModel.saveNewsToDB(article)
                        }
                        show()
                    }

            }
        }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(fragmentSavedBinding.rvSsvedNews)
        }
    }

    private fun initRecyclerView() {
        Log.i("MYTAG","Inside initRecyclerView of Saved Fragment")
        fragmentSavedBinding.rvSsvedNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}
