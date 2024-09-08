package com.lmorda.pullpage.domain

import com.google.gson.annotations.SerializedName

data class GithubListDetailsDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String?,

    @SerializedName("forks_count")
    val forksCount: Int?,

    @SerializedName("stargazers_count")
    val stargazersCount: Int?,

    @SerializedName("owner")
    val owner: GIthubOwnerDto,
)
