package com.pemmob.responsi.data.model

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("docs") val docs: List<Book>?
)
