package com.pemmob.responsi.data.model

import com.google.gson.annotations.SerializedName

// Null safety: Semua properti nullable
data class Book(
    @SerializedName("key") val key: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("author_name") val authorName: List<String>?,
    @SerializedName("first_publish_year") val firstPublishYear: Int?,
    @SerializedName("edition_count") val editionCount: Int?,
    @SerializedName("language") val language: List<String>?
)
