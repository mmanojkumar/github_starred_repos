package com.github.data.repository

import androidx.test.platform.app.InstrumentationRegistry
import com.github.data.api.APIConstants.GITHUB_URL
import com.github.data.network.NetworkModule
import com.github.data.network.RestClient
import com.github.domain.DefaultObserver
import com.github.domain.model.StarredRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import junit.framework.TestCase
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


@RunWith(RobolectricTestRunner::class)
class GitHubDataRepositoryTest : TestCase() {

    private var gitHubDataRepository:GitHubDataRepository? = null
    private val networkModule = NetworkModule()

    @Before
    public override fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val restClient = RestClient(networkModule.retrofit(networkModule.okHttpClient(context!!), networkModule.url()))
        gitHubDataRepository = GitHubDataRepository(restClient)
    }

    @Test
    fun testFetchMostStarredRepositoriesForIncorrectPageNumber() {
        val countDownLatch =  CountDownLatch(1);
        var exception:Throwable? = null

        val observable = gitHubDataRepository?.fetchMostStarredRepositories(100000000)
        observable!!.subscribeOn(Schedulers.newThread())
        observable.observeOn(AndroidSchedulers.mainThread())
        observable.subscribeWith(object: DefaultObserver<StarredRepository>() {
            override fun onError(e: Throwable?) {
                exception = e
            }

            override fun onNext(it: StarredRepository?) {
            }

            override fun onComplete() {
                super.onComplete()
            }
        })

        countDownLatch.await(3000, TimeUnit.MILLISECONDS)
        if(exception != null) {
            Assert.fail(exception?.message)
        }
    }

    @Test
    fun testFetchMostStarredRepositories() {
        val countDownLatch =  CountDownLatch(1);
        var exception:Throwable? = null
        var result:StarredRepository? = null
        val observable = gitHubDataRepository?.fetchMostStarredRepositories(1)
        observable!!.subscribeOn(Schedulers.newThread())
        observable.observeOn(AndroidSchedulers.mainThread())
        observable.subscribeWith(object: DefaultObserver<StarredRepository>() {
            override fun onError(e: Throwable?) {
                exception = e
            }

            override fun onNext(it: StarredRepository?) {
                result = it
            }

            override fun onComplete() {
                super.onComplete()
            }
        })
        countDownLatch.await(3000, TimeUnit.MILLISECONDS)
        assertTrue(result!!.repositoryModels.isNotEmpty())
        result?.repositoryModels?.forEach { repository ->
            assertNotNull(repository.repositoryName)
            assertNotNull(repository.userName)
            assertNotNull(repository.description)
            assert(repository.numberOfStars > 0)
        }

    }

    @Test
    fun testGitHubUrl() {
        assert(GITHUB_URL == networkModule.url())
    }


}