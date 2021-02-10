package com.github.data.repository

import com.github.data.network.RestClient
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GitHubDataRepositoryTest {

    private var gitHubDataRepository:GitHubDataRepository? = null
    private var gitHubDataRepository:GitHubDataRepository? = null

    @Before
    public  fun setUp() {
        gitHubDataRepository = GitHubDataRepository(RestClient(NetworkModule.))
    }

    public override fun tearDown() {}

    fun testFetchMostStarredRepositories() {}

    fun testGetRestClient() {}

    fun testSetRestClient() {}
}