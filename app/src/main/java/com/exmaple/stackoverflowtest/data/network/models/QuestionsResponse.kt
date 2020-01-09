package com.exmaple.stackoverflowtest.data.network.models

import com.exmaple.stackoverflowtest.data.models.Question
import com.squareup.moshi.Json

data class QuestionsResponse(
    var items: List<Question>,
    @Json(name = "has_more") var hasMore: Boolean
)