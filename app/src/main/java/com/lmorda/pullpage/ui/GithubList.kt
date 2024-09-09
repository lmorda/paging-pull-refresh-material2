package com.lmorda.pullpage.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.lmorda.pullpage.domain.GithubListDetailsDto

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun GithubList(
    innerPadding: PaddingValues,
    viewModel: GithubListViewModel,
) {
    val githubRepos = viewModel.repos.collectAsLazyPagingItems()

    val isPullToRefreshing = remember { mutableStateOf(false) }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = isPullToRefreshing.value,
        onRefresh = {
            isPullToRefreshing.value = true
            githubRepos.refresh() // Trigger a refresh
        }
    )

    Box(
        modifier = Modifier
            .pullRefresh(pullRefreshState)
            .fillMaxSize()
    ) {
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            items(count = githubRepos.itemCount) { index ->
                GithubRepoItem(repo = githubRepos[index])
            }
            // Handle pull to refresh indicator
            if (githubRepos.loadState.refresh !is LoadState.Loading) {
                isPullToRefreshing.value = false
            }
            // Display loading indicator for initial load
            if (githubRepos.loadState.refresh == LoadState.Loading) {
                item { PagingLoadingIndicator() }
            }
            // Display loading indicator for additional pages
            if (githubRepos.loadState.append == LoadState.Loading) {
                item { PagingLoadingIndicator() }
            }
        }

        PullRefreshIndicator(
            modifier = Modifier.align(Alignment.TopCenter),
            refreshing = githubRepos.loadState.refresh == LoadState.Loading,
            state = pullRefreshState,
        )
    }
}

@Composable
private fun GithubRepoItem(repo: GithubListDetailsDto?) {
    repo?.let {
        GithubListDetails(details = it)
    }
}

@Composable
private fun PagingLoadingIndicator() {
    CircularProgressIndicator(
        modifier = Modifier
            .padding(top = 24.dp)
            .fillMaxWidth()
            .wrapContentWidth(
                align = Alignment.CenterHorizontally,
            )
    )
}
