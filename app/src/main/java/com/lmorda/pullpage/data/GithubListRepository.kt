package com.lmorda.pullpage.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.lmorda.pullpage.domain.GithubListDetailsDto
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.github.com/"
const val PAGE_SIZE = 10

class GithubListRepository {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val apiService = retrofit.create(GithubApiService::class.java)

    fun getRepositoriesStream(): Flow<PagingData<GithubListDetailsDto>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { GithubListPagingSource(apiService) }
        ).flow
    }
}
