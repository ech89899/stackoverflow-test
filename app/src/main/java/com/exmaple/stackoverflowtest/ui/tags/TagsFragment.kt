package com.exmaple.stackoverflowtest.ui.tags

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.exmaple.stackoverflowtest.R
import com.exmaple.stackoverflowtest.data.models.Tag
import com.exmaple.stackoverflowtest.databinding.TagsFragmentBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.tags_fragment.*
import java.lang.Exception

class TagsFragment : Fragment() {
    private val TAG = TagsFragment::class.simpleName

    companion object {
        fun newInstance() = TagsFragment()
    }

    private lateinit var model: TagsViewModel
    private val adapter: TagsAdapter = TagsAdapter(mutableListOf()) { tag -> model.onTagSelected(tag) }

    private lateinit var binding: TagsFragmentBinding
    private lateinit var scrollListener: RecyclerView.OnScrollListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        binding = TagsFragmentBinding.inflate(inflater, container, false);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listTags.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        model = activity?.run {
            ViewModelProviders.of(this)[TagsViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

        model.tags.observe(this, Observer<MutableList<Tag>> { tags ->
            Log.d(TAG, "tags: size=${tags.size}")
            if (tags.isNotEmpty()) {
                adapter.updateItems(tags)
                scrollListener = createOnScrollListener()
                binding.listTags.addOnScrollListener(scrollListener)
            }
        })

        model.isLoading.observe(this, Observer<Boolean> { isLoading ->
            if (isLoading) {
                showProgress()
            }
            else {
                hideProgress()
            }
        })

        model.onError().observe(this, Observer<String?> { errorMessage ->
            errorMessage?.let {
                showError(it)
            }
        })
    }

    private val lastVisibleItemPosition: Int
        get() = (binding.listTags.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

    private fun createOnScrollListener(): RecyclerView.OnScrollListener {
        return object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                val totalItemCount = recyclerView.layoutManager?.itemCount
                if (totalItemCount == lastVisibleItemPosition + 1) {
                    binding.listTags.removeOnScrollListener(scrollListener)
                    model.loadTags()
                }
            }
        }
    }

    private fun showProgress() {
        binding.progress.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        binding.progress.visibility = View.INVISIBLE
    }

    private fun showError(message: String) {
        view?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_INDEFINITE).show()
        }
    }
}
