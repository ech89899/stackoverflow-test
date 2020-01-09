package com.exmaple.stackoverflowtest.data.network.models

import com.exmaple.stackoverflowtest.data.models.Tag
import com.squareup.moshi.Json

data class TagsResponse(
    var items: List<Tag>,
    @Json(name = "has_more") var hasMore: Boolean
)