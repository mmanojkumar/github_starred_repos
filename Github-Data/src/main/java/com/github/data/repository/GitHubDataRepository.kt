package com.github.data.repository


import com.github.data.api.GitHubApi
import com.github.data.mapper.GitHubEntityDataMapper
import com.github.data.network.RestClient
import com.github.data.utils.DateUtils
import com.github.domain.model.StarredRepository
import com.github.domain.repository.GitHubRepository
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GitHubDataRepository @Inject constructor(var restClient: RestClient) : GitHubRepository {

    override fun fetchMostStarredRepositories(pageNumber: Int): Observable<StarredRepository> {
        return restClient.retrofit.create(GitHubApi::class.java).execute(
            "created:" + DateUtils.today(),
            "stars",
            "desc",
            pageNumber
        ).map(
            GitHubEntityDataMapper::toStarredRepository
        )
    }

}