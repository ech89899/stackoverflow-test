package com.exmaple.stackoverflowtest.ui.tags

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exmaple.stackoverflowtest.data.Repository
import com.exmaple.stackoverflowtest.data.models.Tag
import kotlinx.coroutines.launch

class TagsViewModel : ViewModel() {
    private val TAG = TagsViewModel::class.simpleName

    private val repo: Repository = Repository()

    private var _tags: MutableLiveData<MutableList<Tag>> = MutableLiveData()
    private var _selectedTag: MutableLiveData<Tag?> = MutableLiveData()
    private var _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    private var _errorMessage: MutableLiveData<String?> = MutableLiveData()

    private var nextPage = 1

    init {
        _tags.value = mutableListOf()
        _isLoading.value = false
        loadTags()
    }

    val tags: LiveData<MutableList<Tag>>
        get() = _tags

    val selctedTag: LiveData<Tag?>
        get() = _selectedTag

    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun onError(): LiveData<String?> {
        return _errorMessage
    }

    fun onTagSelected(tag: Tag?) {
        Log.d(TAG, "onTagSelected(): tag=${tag?.name}")
        _selectedTag.value = tag
    }

    fun loadTags() {
        if (nextPage <= 0) {
            return
        }
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val result = repo.loadTags(nextPage)
                val newValue: MutableList<Tag>? = _tags.value
                newValue?.addAll(result.items)
                _tags.value = newValue
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
