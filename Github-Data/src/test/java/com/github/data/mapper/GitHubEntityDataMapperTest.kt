package com.github.data.mapper

import com.github.data.entity.GitHubResponse
import com.github.data.entity.OwnerEntity
import com.github.data.entity.RepositoryEntity
import junit.framework.TestCase
import org.junit.Test

class GitHubEntityDataMapperTest : TestCase() {

    @Test
    fun testToStarredRepository() {
        val list = mutableListOf(
            RepositoryEntity("name", OwnerEntity("username"), 3, "description"),
            RepositoryEntity("name", OwnerEntity("username"), 3, "description")
        )
        val response =
            GitHubEntityDataMapper.toStarredRepository(GitHubResponse(100, list))
        assert(response.repositoryModels.isNotEmpty())
    }

    @Test
    fun testToStarredRepositoryWithEmptyNullDescription() {
        val list = mutableListOf(
            RepositoryEntity("name", OwnerEntity("username"), 3, null),
            RepositoryEntity("name", OwnerEntity("username"), 3, null)
        )
        val response =
            GitHubEntityDataMapper.toStarredRepository(GitHubResponse(100, list))
        assert(response.repositoryModels.isNotEmpty())
    }
}