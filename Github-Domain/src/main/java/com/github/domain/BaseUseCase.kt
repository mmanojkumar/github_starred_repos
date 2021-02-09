package com.github.domain

import com.github.domain.executor.PostExecutionThread
import com.github.domain.executor.ThreadExecutor
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

abstract class BaseUseCase<T, Params> {

    @Inject
    lateinit var threadExecutor: ThreadExecutor
    @Inject
    lateinit var postExecutionThread: PostExecutionThread
    private val disposables: CompositeDisposable = CompositeDisposable()

    abstract fun buildUseCaseObservable(params: Params?): Observable<T>?

    fun execute(params: Params?, observer: DisposableObserver<T>) {
        val observable = buildUseCaseObservable(params)
            ?.subscribeOn(Schedulers.from(threadExecutor))
            ?.observeOn(postExecutionThread.scheduler)
        disposables.add(observable?.subscribeWith(observer))
    }

    open fun dispose() {
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
    }


    private fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

}