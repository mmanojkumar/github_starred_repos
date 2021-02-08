package com.github.presentation.fragment


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.repository.presentation.R


abstract class BaseFragment : Fragment() {


    abstract fun initUIComponents()
    abstract fun initObservers()
    abstract fun initDaggerDependencies()
    abstract fun getTitle(): String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDaggerDependencies()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUIComponents()
        initObservers()
    }

    override fun onResume() {
        super.onResume()
        setTitle()
    }

    private fun setTitle() {
        activity?.title = getTitle()
    }

    fun isTablet(): Boolean {
        return resources.getBoolean(R.bool.isTablet)
    }


    fun replaceFragment(
        containerViewId: Int,
        fragment: Fragment,
        allowBackStack: Boolean = true
    ) {
        FragmentUtil.replaceFragment(activity!!, containerViewId, fragment, allowBackStack)
    }


}