package com.exmaple.stackoverflowtest.ui.questions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exmaple.stackoverflowtest.data.Repository
import com.exmaple.stackoverflowtest.data.models.Question
import com.exmaple.stackoverflowtest.data.models.Tag
import kotlinx.coroutines.launch

class QuestionsViewModel : ViewModel() {
    private val TAG = QuestionsViewModel::class.simpleName

    private val repo: Repository = Repository()

    private var _questions: MutableLiveData<MutableList<Question>> = MutableLiveData()
    private var _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    private var _errorMessage: MutableLiveData<String?> = MutableLiveData()

    private lateinit var _tag: String

    private var nextPage = 1

    init {
        _questions.value = mutableListOf()
        _isLoading.value = false
    }

    val questions: LiveData<MutableList<Question>>
        get() = _questions

    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun onError(): LiveData<String?> {
        return _errorMessage
    }

    var tagName: String
        get() = _tag
        set(value) {
            _tag = value
            _questions.value = mutableListOf()
            _isLoading.value = false
            nextPage = 1
            loadQuestions()
        }

    fun loadQuestions() {
        if (nextPage <= 0) {
            return
        }
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val result = repo.loadQuestions(_tag, nextPage)
                val newValue: MutableList<Question>? = _questions.value
                newValue?.addAll(result.items)
                _questions.value = newValue
                if (result.hasMore) {
                    nextPage++
                }
                else {
                    nextPage = 0;
                }
                _errorMessage.value = null
            }
            catch (error: Throwable) {
                _errorMessage.value = error.localizedMessage
            }
            finally {
                _isLoading.value = false
            }
        }
    }
}
