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
import com.github.presentation.PaginationScrollListener
import com.github.presentation.adapter.RepositoryListAdapter
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

    var pagination = Pagination()


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

        val repositoryListAdapter = RepositoryListAdapter(mutableListOf())
        repositoryListAdapter.onItemClickListener =
            object : RepositoryListAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    //Do nothing for now
                }
            }
        repositoryListFragmentBinding.repositoryRecyclerView.adapter = repositoryListAdapter
        repositoryListFragmentBinding.repositoryRecyclerView.addOnScrollListener(object :
            PaginationScrollListener(linearLayoutManager) {
            override fun    loadMoreItems() {
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
        })

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
            when (it) {
                is NoInternetException -> {
                    showErrorMessage(getString(R.string.no_internet_connection))
                }
                is ApiException -> {
                    showErrorMessage(it.message)
                }
                else -> {
                    showErrorMessage(getString(R.string.generic_error_message))
                }
            }
        })

        repositoryListViewModel.loading.observe(this, Observer {
            if (it) {
                hideErrorMessage()
                hideRepositories()
                showShimmer()
            } else {
                hideShimmer()
            }
        })
    }

    private fun setLoadingFooterVisibility() {
        val repositoryListAdapter: RepositoryListAdapter =
            repositoryListFragmentBinding.repositoryRecyclerView.adapter as RepositoryListAdapter
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

    private fun hideErrorMessage() {
        repositoryListFragmentBinding.errorMessage.visibility = View.GONE
        repositoryListFragmentBinding.retry.visibility = View.GONE
    }

    private fun hideRepositories() {
        repositoryListFragmentBinding.repositoryRecyclerView.visibility = View.GONE
    }


    private fun showRepositories(it: List<RepositoryModel>) {
        val repositoryListAdapter: RepositoryListAdapter =
            repositoryListFragmentBinding.repositoryRecyclerView.adapter as RepositoryListAdapter
        repositoryListAdapter.repositories.addAll(it)
        repositoryListAdapter.notifyDataSetChanged()
        repositoryListFragmentBinding.repositoryRecyclerView.visibility = View.VISIBLE
    }


}