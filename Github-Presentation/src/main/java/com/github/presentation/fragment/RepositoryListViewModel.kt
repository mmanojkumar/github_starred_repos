package com.github.presentation.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.domain.interactor.DefaultObserver
import com.github.domain.interactor.GetMostStarredRepositoryUseCase
import com.github.domain.model.StarredRepository
import com.github.presentation.mapper.GithubModelMapper
import com.github.presentation.model.StarredRepositoryModel
import javax.inject.Inject

class RepositoryListViewModel @Inject constructor(var mostStarredRepositoryUseCase: GetMostStarredRepositoryUseCase) :
    ViewModel() {

    val loading: MutableLiveData<Boolean> = MutableLiveData()
    val failure: MutableLiveData<Throwable> = MutableLiveData()
    val success: MutableLiveData<StarredRepositoryModel> = MutableLiveData()

    fun getMostStarredRepositories(pageNumber:Int) {
        if(pageNumber == 1) {
            loading.postValue(true)
        }
        mostStarredRepositoryUseCase.execute(
            GetMostStarredRepositoryUseCase.Params(pageNumber),
            object : DefaultObserver<StarredRepository>() {
            override fun onNext(starredRepository: StarredRepository) {
                success.postValue(GithubModelMapper.toStarredRepository(starredRepository))
            }

            override fun onComplete() {
                if(pageNumber == 1) {
                    loading.postValue(false)
                }
            }

            override fun onError(exception: Throwable?) {
                loading.postValue(false)
                failure.postValue(exception)
            }
        })
    }


    override fun onCleared() {
        super.onCleared()
        mostStarredRepositoryUseCase.dispose()
    }

}