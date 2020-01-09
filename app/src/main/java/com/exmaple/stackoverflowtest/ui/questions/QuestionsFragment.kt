package com.exmaple.stackoverflowtest.ui.questions

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.exmaple.stackoverflowtest.data.models.Question
import com.exmaple.stackoverflowtest.databinding.QuestionsFragmentBinding
import com.google.android.material.snackbar.Snackbar

class QuestionsFragment : Fragment() {
    private val TAG = QuestionsFragment::class.simpleName

    companion object {
        fun newInstance() = QuestionsFragment()
    }

    private lateinit var model: QuestionsViewModel
    private val adapter: QuestionsAdapter = QuestionsAdapter(mutableListOf())

    private lateinit var binding: QuestionsFragmentBinding
    private lateinit var scrollListener: RecyclerView.OnScrollListener


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View {
        binding = QuestionsFragmentBinding.inflate(inflater, container, false);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listQuestions.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        model = activity?.run {
            ViewModelProviders.of(this)[QuestionsViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

        model.questions.observe(this, Observer<MutableList<Question>> { questions ->
            Log.d(TAG, "questions: size=${questions.size}")
            if (questions.isNotEmpty()) {
                adapter.updateItems(questions)
                scrollListener = createOnScrollListener()
                binding.listQuestions.addOnScrollListener(scrollListener)
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
        get() = (binding.listQuestions.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

    private fun createOnScrollListener(): RecyclerView.OnScrollListener {
        return object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                val totalItemCount = recyclerView.layoutManager?.itemCount
                if (totalItemCount == lastVisibleItemPosition + 1) {
                    binding.listQuestions.removeOnScrollListener(scrollListener)
                    model.loadQuestions()
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
