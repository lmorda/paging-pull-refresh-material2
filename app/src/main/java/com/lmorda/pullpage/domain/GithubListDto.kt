package com.lmorda.pullpage.domain

import com.google.gson.annotations.SerializedName

data class GithubListDto(

    @SerializedName("items")
    val items: List<GithubListDetailsDto>,
)
