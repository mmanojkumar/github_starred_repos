package com.github.domain.interactor

import com.github.domain.BaseUseCase
import com.github.domain.model.StarredRepository
import com.github.domain.repository.GitHubRepository
import io.reactivex.Observable
import javax.inject.Inject


class GetMostStarredRepositoryUseCase @Inject constructor(var gitHubRepository: GitHubRepository) :
    BaseUseCase<StarredRepository, GetMostStarredRepositoryUseCase.Params>() {

    override fun buildUseCaseObservable(params: Params?): Observable<StarredRepository> {
        return gitHubRepository.fetchMostStarredRepositories(params!!.pageNumber)
    }

    class Params constructor(val pageNumber: Int)


}