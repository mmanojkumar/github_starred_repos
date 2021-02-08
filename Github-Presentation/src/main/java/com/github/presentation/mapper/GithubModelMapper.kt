package com.github.presentation.mapper


import com.github.domain.model.StarredRepository
import com.github.presentation.model.RepositoryModel
import com.github.presentation.model.StarredRepositoryModel

object GithubModelMapper {

    fun toStarredRepository(starredRepository: StarredRepository):StarredRepositoryModel {
        val repositoryModels = mutableListOf<RepositoryModel>()
        starredRepository.repositoryModels.forEach {
            repositoryModels.add(
                RepositoryModel(
                    it.repositoryName,
                    it.userName,
                    it.description,
                    it.numberOfStars
                )
            )
        }

        return StarredRepositoryModel(starredRepository.totalCount, repositoryModels)
    }

}