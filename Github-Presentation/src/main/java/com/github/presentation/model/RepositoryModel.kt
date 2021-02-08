package com.github.presentation.model

data class RepositoryModel(
    val repositoryName: String,
    val userName: String,
    val description: String?,
    val numberOfStars: Int
) {

    constructor() : this("", "", "", 0)
}

data class StarredRepositoryModel(val totalCount: Int, val repositoryModels: List<RepositoryModel>)