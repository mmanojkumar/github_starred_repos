package com.github.domain.model

data class Repository(
    val repositoryName: String,
    val userName: String,
    var description: String?,
    val numberOfStars: Int)


data class StarredRepository(val totalCount:Int, val repositoryModels: List<Repository>)