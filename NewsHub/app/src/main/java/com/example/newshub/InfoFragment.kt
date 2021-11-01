package com.example.newshub

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import com.example.newshub.data.model.Article
import com.example.newshub.databinding.FragmentInfoBinding
import com.example.newshub.presentation.ViewModel.NewsViewModel

class InfoFragment : Fragment() {

    lateinit var fragmentInfoBinding: FragmentInfoBinding
    lateinit var viewModel: NewsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentInfoBinding = FragmentInfoBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel

        val article = requireArguments().getSerializable("selected_item") as Article
        fragmentInfoBinding.infoWebView.apply {
            webViewClient = WebViewClient()
            if(article!!.url!="") {
                loadUrl(article.url.toString())
            }
        }
        fragmentInfoBinding.floatingActionButton.setOnClickListener{
            viewModel.saveNewsToDB(article)
            Toast.makeText(activity,"Saved Successfully",Toast.LENGTH_SHORT).show()
         }

        Log.i("MYTAG", "............selected item .......------->>>>" +article)
    }
}