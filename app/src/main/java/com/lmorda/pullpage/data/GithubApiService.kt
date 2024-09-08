package com.lmorda.pullpage.data

import com.lmorda.pullpage.domain.GithubListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApiService {
    @GET("search/repositories")
    suspend fun getRepositories(
        @Query("q") query: String = "google",
        @Query("sort") sort: String = "stars",
        @Query("order") order: String = "desc",
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): GithubListDto
}
