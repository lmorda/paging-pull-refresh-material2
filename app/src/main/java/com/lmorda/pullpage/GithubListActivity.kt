package com.lmorda.pullpage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.lmorda.pullpage.domain.GithubListDetailsDto

class GithubListActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Scaffold(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxSize(),
            ) { innerPadding ->
                val viewModel = viewModel<GithubListViewModel>()
                GithubList(
                    innerPadding = innerPadding,
                    viewModel = viewModel,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GithubList(
    innerPadding: PaddingValues,
    viewModel: GithubListViewModel,
) {
    val githubRepos = viewModel.repos.collectAsLazyPagingItems()

    val isSwipeToRefreshing = remember { mutableStateOf(false) }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = isSwipeToRefreshing.value,
        onRefresh = {
            isSwipeToRefreshing.value = true
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
                isSwipeToRefreshing.value = false
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
            .fillMaxWidth()
            .wrapContentWidth(
                align = Alignment.CenterHorizontally,
            )
    )
}
