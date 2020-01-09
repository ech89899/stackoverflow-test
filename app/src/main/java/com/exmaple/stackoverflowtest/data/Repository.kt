package com.exmaple.stackoverflowtest.data

import android.util.Log
import com.exmaple.stackoverflowtest.data.models.Tag
import com.exmaple.stackoverflowtest.data.network.StackOverflowApi
import com.exmaple.stackoverflowtest.data.network.models.QuestionsResponse
import com.exmaple.stackoverflowtest.data.network.models.TagsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository {
    private val TAG = Repository::class.simpleName

    suspend fun loadTags(page: Int = 1): TagsResponse {
        Log.d(TAG, "loadTags(): page=$page")
        return StackOverflowApi.getTags(page)
    }

    suspend fun loadQuestions(tag: String, page: Int = 1): QuestionsResponse {
        Log.d(TAG, "loadQuestions(): tag=${tag} page=$page")
        return StackOverflowApi.getQuestions(tag, page)
    }
}