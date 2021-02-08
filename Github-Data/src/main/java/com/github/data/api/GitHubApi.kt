package com.github.data.api

import com.github.data.DataConstants
import com.github.data.entity.GitHubResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApi {

    @GET("/search/repositories")
    fun execute(
        @Query("q") createdDate: String,
        @Query("sort") sortValue: String = "stars",
        @Query("order") orderValue: String = "desc",
        @Query("page") pageNumber: Int = 1,
        @Query("per_page") perPage: Int = DataConstants.RESULT_PER_PAGE
    ): Observable<GitHubResponse>
}