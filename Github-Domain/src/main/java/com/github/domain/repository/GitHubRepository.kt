package com.github.domain.repository

import com.github.domain.model.StarredRepository
import io.reactivex.Observable

interface GitHubRepository {
    fun fetchMostStarredRepositories(pageNumber:Int): Observable<StarredRepository>
}