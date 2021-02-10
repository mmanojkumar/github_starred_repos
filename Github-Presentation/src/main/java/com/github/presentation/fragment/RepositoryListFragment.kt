package com.github.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.data.exception.ApiException
import com.github.data.exception.NoInternetException
import com.github.presentation.AndroidApplication
import com.github.presentation.adapter.RepositoryListAdapter
import com.github.presentation.com.github.presentation.listener.PaginationScrollListener
import com.github.presentation.com.github.presentation.viewmodel.RepositoryListViewModel
import com.github.presentation.di.component.DaggerGithubComponent
import com.github.presentation.di.module.ActivityModule
import com.github.presentation.model.Pagination
import com.github.presentation.model.RepositoryModel
import com.repository.presentation.R
import com.repository.presentation.databinding.RepositoryListFragmentBinding
import javax.inject.Inject


class RepositoryListFragment : BaseFragment() {

    @Inject
    lateinit var repositoryListViewModel: RepositoryListViewModel
    private lateinit var repositoryListFragmentBinding: RepositoryListFragmentBinding
    private val repositoryListAdapter = RepositoryListAdapter(mutableListOf())
    private var pagination = Pagination(resultPerPage = 30)



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        repositoryListFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.repository_list_fragment,
            container, false
        )
        repositoryListFragmentBinding.repositoryListViewModel = repositoryListViewModel
        return repositoryListFragmentBinding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repositoryListViewModel.getMostStarredRepositories(pagination.currentPageNumber)
    }

    override fun initDaggerDependencies() {
        activity?.let {
            DaggerGithubComponent.builder().applicationComponent(
                (activity?.application as AndroidApplication).applicationComponent
            ).activityModule(ActivityModule(it as AppCompatActivity)).build().inject(this)
        }
    }


    override fun initUIComponents() {

        val linearLayoutManager = LinearLayoutManager(context)
        repositoryListFragmentBinding.repositoryRecyclerView.layoutManager =
            linearLayoutManager
        repositoryListFragmentBinding.repositoryRecyclerView.adapter = repositoryListAdapter
        repositoryListFragmentBinding.repositoryRecyclerView.addOnScrollListener(getPageScrollListener(linearLayoutManager))

        repositoryListAdapter.onItemClickListener = onItemClickListener()

        repositoryListFragmentBinding.retry.setOnClickListener {
            pagination.currentPageNumber += 1
            repositoryListViewModel.getMostStarredRepositories(pagination.currentPageNumber)
        }

    }


    override fun initObservers() {
        repositoryListViewModel.success.observe(this, Observer {
            pagination.apply {
                totalCount = it.totalCount
            }
            if (it.repositoryModels.isNotEmpty()) {
                showRepositories(it.repositoryModels)
                pagination.isLoading = false
                setLoadingFooterVisibility()
            }
        })

        repositoryListViewModel.failure.observe(this, Observer {
            pagination.currentPageNumber -= 1
            handleFailure(it)
        })

        repositoryListViewModel.loading.observe(this, Observer {
            if (it) {
                hideRetryComponent()
                hideRepositoriesComponent()
                showShimmer()
            } else {
                hideShimmer()
            }
        })
    }

    private fun handleFailure(it: Throwable) {
        val errorMessage = when (it) {
            is NoInternetException -> {
                getString(R.string.no_internet_connection)
            }
            is ApiException -> {
                it.message
            }
            else -> {
                getString(R.string.generic_error_message)
            }
        }
        if (pagination.currentPageNumber >= 1) {
            repositoryListAdapter.showRetry(errorMessage)
        } else {
            showErrorMessage(errorMessage)
        }
    }

    private fun setLoadingFooterVisibility() {
        if (pagination.isLastPage()) {
            repositoryListAdapter.removeLoadingFooter()
        } else {
            repositoryListAdapter.addLoadingFooter()
        }
    }

    override fun getTitle(): String {
        return getString(R.string.repositories)
    }

    private fun hideShimmer() {
        repositoryListFragmentBinding.includeShimmer.shimmer.stopShimmer()
        repositoryListFragmentBinding.includeShimmer.shimmer.visibility = View.GONE
    }

    private fun showShimmer() {
        repositoryListFragmentBinding.includeShimmer.shimmer.visibility = View.VISIBLE
        repositoryListFragmentBinding.includeShimmer.shimmer.startShimmer()
    }

    private fun showErrorMessage(errorMessage: String) {
        repositoryListFragmentBinding.errorMessage.text = errorMessage
        repositoryListFragmentBinding.errorMessage.visibility = View.VISIBLE
        repositoryListFragmentBinding.retry.visibility = View.VISIBLE
    }

    private fun hideRetryComponent() {
        repositoryListFragmentBinding.errorMessage.visibility = View.GONE
        repositoryListFragmentBinding.retry.visibility = View.GONE
    }

    private fun hideRepositoriesComponent() {
        repositoryListFragmentBinding.repositoryRecyclerView.visibility = View.GONE
    }


    private fun showRepositories(it: List<RepositoryModel>) {
        repositoryListAdapter.hideRetryError()
        repositoryListAdapter.repositories.addAll(it)
        repositoryListAdapter.notifyDataSetChanged()
        repositoryListFragmentBinding.repositoryRecyclerView.visibility = View.VISIBLE
    }


    private fun getPageScrollListener(linearLayoutManager:LinearLayoutManager): PaginationScrollListener {
        return object :
            PaginationScrollListener(linearLayoutManager) {
            override fun  loadMoreItems() {
                pagination.isLoading = true
                pagination.currentPageNumber += 1
                repositoryListViewModel.getMostStarredRepositories(pagination.currentPageNumber)
            }

            override val totalPageCount: Int
                get() = pagination.totalPageCount()
            override val isLastPage: Boolean
                get() = pagination.isLastPage()
            override val isLoading: Boolean
                get() = pagination.isLoading
        }
    }

    private fun onItemClickListener(): RepositoryListAdapter.OnItemClickListener {
        return object : RepositoryListAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                //Do nothing for now
            }

            override fun onRetryClick() {
                pagination.currentPageNumber += 1
                repositoryListViewModel.getMostStarredRepositories(pagination.currentPageNumber)
            }
        }
    }

}