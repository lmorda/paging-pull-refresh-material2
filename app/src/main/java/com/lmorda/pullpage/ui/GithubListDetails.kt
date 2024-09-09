package com.lmorda.pullpage.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.lmorda.pullpage.R
import com.lmorda.pullpage.domain.GithubOwnerDto
import com.lmorda.pullpage.domain.GithubListDetailsDto

@Composable
fun GithubListDetails(details: GithubListDetailsDto) {
    Column(
        modifier = Modifier.padding(
            horizontal = 16.dp,
            vertical = 8.dp,
        )
    ) {
        GithubDetailsHeader(details)
        details.description?.let {
            ExploreItemDescription(description = it)
        }
        details.stargazersCount?.let {
            GithubDetailsStargazers(count = it)
        }
        details.forksCount?.let {
            ExploreItemForks(count = it)
        }
        Divider()
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun GithubDetailsHeader(details: GithubListDetailsDto) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        details.owner.avatarUrl.takeIf { !it.isNullOrBlank() }?.let {
            GlideImage(
                modifier = Modifier
                    .size(size = 40.dp)
                    .clip(shape = CircleShape),
                model = details.owner.avatarUrl,
                contentDescription = details.owner.avatarUrl,
            )
        }
        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(
                text = details.name,
                style = MaterialTheme.typography.h5,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            details.owner.login?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.h6,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@Composable
private fun ExploreItemDescription(description: String) {
    if (description.isNotBlank()) {
        Text(
            modifier = Modifier.padding(top = 2.dp),
            text = description,
            style = MaterialTheme.typography.body1,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
private fun ExploreItemForks(count: Int) {
    Row(
        modifier = Modifier.padding(top = 2.dp),
    ) {
        Icon(
            imageVector = Icons.Filled.Build,
            contentDescription = null,
            tint = Color.Green,
        )
        Text(
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically),
            text = stringResource(
                R.string.forks_under_construction,
                countPrettyString(count),
            ),
            style = MaterialTheme.typography.body1,
        )
    }
}

@Composable
private fun GithubDetailsStargazers(count: Int) {
    Row(
        modifier = Modifier.padding(top = 2.dp),
    ) {
        Icon(
            imageVector = Icons.Default.Star,
            tint = Color.Yellow,
            contentDescription = null,
        )
        Text(
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically),
            text = stringResource(
                id = R.string.stargazers,
                countPrettyString(count),
            ),
            style = MaterialTheme.typography.body1,
        )
    }
}

private fun countPrettyString(value: Int): String {
    return when {
        value >= 1_000_000 -> "${"%.1f".format(value / 1_000_000.0)}M"
        value >= 1_000 -> "${"%.1f".format(value / 1_000.0)}k"
        else -> value.toString()
    }
}

@Preview
@Composable
fun GithubListDetailsPreview() {
    GithubListDetails(
        details = GithubListDetailsDto(
            id = 0,
            name = "my-application",
            owner = GithubOwnerDto("google", ""),
            description = "description for google my application",
            stargazersCount = 345123,
            forksCount = 99,
        ),
    )
}
