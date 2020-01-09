package com.exmaple.stackoverflowtest.data.network

import com.exmaple.stackoverflowtest.data.network.models.QuestionsResponse
import com.exmaple.stackoverflowtest.data.network.models.TagsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface IStackOverflowApi {
    @GET("tags")
    suspend fun getTags(@QueryMap params: Map<String, String>): TagsResponse

    @GET("questions")
    suspend fun getQuestions(@QueryMap params: Map<String, String>): QuestionsResponse

}