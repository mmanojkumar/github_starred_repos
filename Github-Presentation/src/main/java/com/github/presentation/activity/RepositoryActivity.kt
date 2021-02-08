package com.github.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.repository.presentation.R
import com.github.presentation.fragment.FragmentUtil
import com.github.presentation.fragment.RepositoryListFragment

class RepositoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.repository_activity)

        FragmentUtil.replaceFragment(
            this,
            R.id.repository_list_container,
            RepositoryListFragment(),
            false
        )
    }


    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }

}