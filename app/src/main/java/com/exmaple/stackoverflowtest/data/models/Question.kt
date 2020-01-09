package com.exmaple.stackoverflowtest.data.models

import com.squareup.moshi.Json

data class Question(
    @Json(name = "creation_date") var creationDate: Long,
    var description: String?,
    var owner: QuestionOwner,
    var title: String
)

data class QuestionOwner(
    @Json(name = "display_name") var displayName: String
)
