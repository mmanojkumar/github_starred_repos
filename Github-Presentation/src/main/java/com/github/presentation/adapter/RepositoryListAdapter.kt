package com.github.presentation.adapter

import android.graphics.Movie
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.github.presentation.adapter.RepositoryListAdapter.ViewType.ITEM
import com.github.presentation.adapter.RepositoryListAdapter.ViewType.LOADING
import com.github.presentation.model.RepositoryModel
import com.repository.presentation.R
import com.repository.presentation.databinding.LoadingItemBinding
import com.repository.presentation.databinding.LoadingItemBindingImpl
import com.repository.presentation.databinding.RepositoryItemBinding


class RepositoryListAdapter(var repositories: MutableList<RepositoryModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var isLoadingAdded = false

    var onItemClickListener: OnItemClickListener? = null

    class RepositoryItemViewHolder(val repositoryItemBinding: RepositoryItemBinding) :
        RecyclerView.ViewHolder(repositoryItemBinding.root)

    class LoadingItemViewHolder(loadingItemBinding: LoadingItemBinding) :
        RecyclerView.ViewHolder(loadingItemBinding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM -> {
                 RepositoryItemViewHolder(
                    RepositoryItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            else ->  LoadingItemViewHolder(
                LoadingItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )

            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == repositories.size - 1 && isLoadingAdded) LOADING else ITEM
    }

    override fun getItemCount(): Int {
        return repositories.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)){
            ITEM -> {
                val repositoryItemBinding = (holder as RepositoryItemViewHolder).repositoryItemBinding
                repositoryItemBinding.repositoryModel = getItem(position)
                repositoryItemBinding.root.setOnClickListener {
                    onItemClickListener?.onItemClick(position)
                }
            }

            LOADING ->{
                //Do Nothing
            }
        }

    }

    private fun getItem(index: Int): RepositoryModel {
        return repositories[index]
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    object ViewType {
        const val LOADING: Int = 1
        const val ITEM: Int = 2
    }

    private fun add(repositoryModel: RepositoryModel) {
        repositories.add(repositoryModel)
        notifyItemInserted(repositories.size - 1)
    }

    fun addLoadingFooter() {
        isLoadingAdded = true
        add(RepositoryModel())
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false
        val position: Int = repositories.size - 1
        repositories.removeAt(position)
        notifyItemRemoved(position)
    }


}