package com.github.data.entity

import com.google.gson.annotations.SerializedName

data class GitHubResponse(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("items") val repositoryEntities: List<RepositoryEntity>
)

data class RepositoryEntity(
    @SerializedName("name") val repositoryName: String,
    @SerializedName("owner") val ownerEntity: OwnerEntity,
    @SerializedName("stargazers_count") val numberOfStar: Int,
    var description: String?
)

data class OwnerEntity(
    @SerializedName("login") val userName: String
)