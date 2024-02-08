package com.wli.test.ui.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.wli.test.R
import com.wli.test.databinding.ActivityMainBinding
import com.wli.test.ui.fragment.MainFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindView(R.layout.activity_main)

        init()
    }

    override fun onBackPressed() {
        finish()
    }

    /**
     * All operations, initialization, data extraction and setting up listeners will starts from here.
     * Purpose is only if there will be a need of redo whole thing than only one method call serves the need.
     */
    private fun init() {
        supportActionBar?.title = "Profile List"
        val mainFragment: Fragment = MainFragment()
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.add(mBinding.fragmentContainerView.id, mainFragment)
        transaction.commit()
    }


}
