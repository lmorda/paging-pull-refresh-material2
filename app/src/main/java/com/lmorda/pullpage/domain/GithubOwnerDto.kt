package com.lmorda.pullpage.domain

import com.google.gson.annotations.SerializedName

data class GithubOwnerDto(
    @SerializedName("login")
    val login: String?,

    @SerializedName("avatar_url")
    val avatarUrl: String?,
)
