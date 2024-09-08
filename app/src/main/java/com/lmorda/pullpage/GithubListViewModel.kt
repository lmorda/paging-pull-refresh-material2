package com.lmorda.pullpage

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.lmorda.pullpage.data.GithubListRepository
import com.lmorda.pullpage.domain.GithubListDetailsDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class GithubListViewModel : ViewModel() {

    private val githubListRepository = GithubListRepository()

    var repos: Flow<PagingData<GithubListDetailsDto>> by mutableStateOf(emptyFlow())

    init {
        repos = githubListRepository.getRepositoriesStream()
    }
}
