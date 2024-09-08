package com.lmorda.pullpage.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

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
