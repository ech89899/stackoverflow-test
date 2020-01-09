package com.exmaple.stackoverflowtest.data.network

import com.exmaple.stackoverflowtest.data.network.models.QuestionsResponse
import com.exmaple.stackoverflowtest.data.network.models.TagsResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "https://api.stackexchange.com/2.2/"

private const val PAGE_SIZE = 20

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

object StackOverflowApi {
    val api : IStackOverflowApi by lazy {
        retrofit.create(IStackOverflowApi::class.java) }

    suspend fun getTags(page: Int = 1): TagsResponse {
        val queryParams = mutableMapOf<String, String>()
        queryParams["order"] = "desc"
        queryParams["sort"] = "popular"
        queryParams["site"] = "stackoverflow"
        queryParams["page"] = page.toString()
        queryParams["pagesize"] = PAGE_SIZE.toString()
        return api.getTags(queryParams)
    }

    suspend fun getQuestions(tag: String, page: Int = 1): QuestionsResponse {
        val queryParams = mutableMapOf<String, String>()
        queryParams["order"] = "desc"
        queryParams["sort"] = "creation"
        queryParams["tagged"] = tag
        queryParams["site"] = "stackoverflow"
        queryParams["page"] = page.toString()
        queryParams["pagesize"] = PAGE_SIZE.toString()
        return api.getQuestions(queryParams)
    }
}