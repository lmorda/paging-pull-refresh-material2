package com.lmorda.pullpage.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lmorda.pullpage.domain.GithubListDetailsDto
import kotlinx.coroutines.delay
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

const val MOCK_API_DELAY = 3000L

class GithubListPagingSource(
    private val apiService: GithubApiService
) : PagingSource<Int, GithubListDetailsDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubListDetailsDto> {
        delay(MOCK_API_DELAY)
        val page = params.key ?: 1
        return try {
            val repositories = apiService.getRepositories(page = page, perPage = params.loadSize)
            Timber.d("Page fetched: $page")
            LoadResult.Page(
                data = repositories.items,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (repositories.items.isEmpty()) null else page + 1
            )
        } catch (e: IOException) {
            Timber.e(e.toString())
            LoadResult.Error(e)
        } catch (e: HttpException) {
            Timber.e(e.toString())
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GithubListDetailsDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
