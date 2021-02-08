package com.github.presentation.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

object FragmentUtil {

    fun replaceFragment(
        activity: FragmentActivity,
        containerViewId: Int,
        fragment: Fragment,
        allowBackStack: Boolean = true
    ) {
        val fragmentTransaction = activity.supportFragmentManager.beginTransaction().replace(
            containerViewId,
            fragment, fragment.javaClass.simpleName
        )
        if (allowBackStack) {
            fragmentTransaction.addToBackStack(fragment.javaClass.simpleName)
        }
        fragmentTransaction.commit()
    }
}