package com.github.data.mapper

import com.github.data.entity.GitHubResponse

import com.github.domain.model.Repository
import com.github.data.entity.RepositoryEntity
import com.github.domain.model.StarredRepository

object GitHubEntityDataMapper {

    fun toStarredRepository(gitHubResponse: GitHubResponse): StarredRepository {
        val repositories = mutableListOf<Repository>()
        gitHubResponse.repositoryEntities.forEach {
            repositories.add(Repository(it.repositoryName,
                it.ownerEntity.userName, if(it.description == null) ""
                else it.description, it.numberOfStar))
        }
        return StarredRepository(gitHubResponse.totalCount, repositories)
    }

}